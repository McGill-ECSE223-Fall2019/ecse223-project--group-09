package ca.mcgill.ecse223.quoridor.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.stream.Collectors;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.JumpMove;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.StepMove;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;

/**
 * This is the controller class for the Quoridor application
 * 
 * @author GROUP-9 (Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, Alixe Delabrousse)
 * @version 04-10-2019
 * 
 * 
 */


public class QuoridorController {

	// ***** Timer Related Associations *****

	/**
	 * This will be how frequently the clock is updated
	 *
	 * @author Paul Teng (260862906)
	 */
	private static final long TIME_PER_TICK_MS = 500;

	/**
	 * Creates a timer that will schedule tasks
	 *
	 * @author Paul Teng (260862906)
	 */
	private static final Timer GLOBAL_CLOCK = new Timer("Global Clock");

	/**
	 * A new mapping is created when the clock starts,
	 * the existing mapping is removed when the click stops
	 *
	 * @author Paul Teng (260862906)
	 */
	private static final HashMap<Player, TimerTask> PLAYER_CLOCK = new HashMap<>();
	
	public static final int INITIAL_ROW = 1;
	public static final int INITIAL_COLUMN = 1;
	public static final Direction INITIAL_ORIENTATION = Direction.Vertical;
	public static final Orientation INITIAL_TO_ORIENTATION = fromDirection(INITIAL_ORIENTATION);
	/////////////////////////// FIELDS ///////////////////////////
	
	private static Game game; 
	private static Player player1; 
	private static Player player2;
	private static Player player3;
	private static Player player4;
	private static Player currentPlayer; // ??? should this be our flag?
	
	/**
	 * 
	 * @author Barry Chen  
	 *
	 * This method create a new empty game  
	 */	
	public Game createGame() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game newGame = new Game(GameStatus.Initializing, null, quoridor);
		return newGame;
	}
	
	/**
	 * 
	 * @author Barry Chan 
	 * 
	 *
	 * This method creates a player
	 * 
	 */	
	public void createPlayer() {

		Game inGame = createGame();
		Player playerUn = new Player(null,null,null);
		Player playerDeux = new Player(null,null,null);
		inGame.setWhitePlayer(playerUn);
		inGame.setBlackPlayer(playerDeux);
	}
	
	/**
	 * 
	 * @author Barry Cheng 
	 * 
	 *
	 * This method sets up a new game 
	 * 
	 */	
	public void startNewGame(Game aGame){
		/*
		When A new game is being initialized
	    And White player chooses a username
	    And Black player chooses a username
	    And Total thinking time is set
	    Then The game shall become ready to start
		 */
		if(aGame.getGameStatus() == GameStatus.Initializing) {
			if((aGame.getWhitePlayer().getUser()!=null)&&(aGame.getBlackPlayer().getUser()!=null)){
				if((aGame.getWhitePlayer().getRemainingTime()!=null)&&(aGame.getBlackPlayer().getRemainingTime()!=null)) {
					aGame.setGameStatus(GameStatus.ReadyToStart);
				}
			}
		}
	}
	
	/*
	 	Scenario: Start clock 
	  	Given The game is ready to start
	  	When I start the clock
	  	Then The game shall be running
	  	And The board shall be initialized
	 */
	
	
	/**
	 * 
	 * @author Barry Chen 
	 * 
	 */
	public Board createNewBoard(){
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board newBoard = new Board(quoridor);
		
		newBoard.setQuoridor(quoridor);
		
		for (int i=0; i<81; i++) {
			int col = (i%9)+1;
			int row = (i/9)+1;
			newBoard.addTile(row, col);
		}
		return newBoard;
	}
	
	/**
	 * 
	 * @author Barry Chen 
	 * 
	 * @param none
	 * @return initialized board
	 * 
	 * This method initialize the board which place both players' pawn at its initial position
	 * 
	 * 
	 */
	
	public void initiateBoard() {
		//throw new UnsupportedOperationException("method initiateBoard is not implemented yet");
		
		/*
		Scenario: Initialize board
		Given The game is ready to start
    	When The initialization of the board is initiated 
    	Then It shall be white player to move
		And White's pawn shall be in its initial position
		And Black's pawn shall be in its initial position
		And All of White's walls shall be in stock
		And All of Black's walls shall be in stock
		And White's clock shall be counting down
		And It shall be shown that this is White's turn
		 */
		
		Board gameBoard = createNewBoard();
		gameBoard.getQuoridor();

		currentPlayer = player1;
		
		for (int i=1; i <= 20; i++) {
			if(i <= 10){
				//add walls for player 1
				player1.addWall(i);
			}
			else{
				//add walls for player 2
				player2.addWall(i);
			}
		}
	}
	
	/**
	 * This method allows the user to select an existing username.
	 * 
	 * @param String user;  
	 * @return void; 
	 * 
	 * @author Ada Andrei
	 */

	public static void selectUsername(String user) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (usernameExists(user)) {
			User.getWithName(user);
		}
	}

	/**
	 * This method allows the user to create a new username.
	 * 
	 * @param String user;  
	 * @return void; 
	 * 
	 * @author Ada Andrei
	 */

	public static void createUsername(String user) throws InvalidInputException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!usernameExists(user))	{
			quoridor.addUser(user);
			User user1 = new User(user, quoridor);
			quoridor.getCurrentGame().getWhitePlayer().setUser(user1); // get players from barry 
			User user2 = new User(user, quoridor);	
			quoridor.getCurrentGame().getWhitePlayer().setUser(user2); // get players from barry 
			//firstPlayer.setNextPlayer(secondPlayer); //gui related
			//secondPlayer.setNextPlayer(firstPlayer); //gui related
		}
		else {
			throw new InvalidInputException("This username already exists, please enter a new one or select the existing username.");
		}
	}


	/**
	 * This method checks if the given username already exists.
	 * 
	 * @param String user;  
	 * @return boolean user; 
	 * 
	 * @author Ada Andrei
	 */

	public static boolean usernameExists(String user) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (User.hasWithName(user) || QuoridorController.getUsernames().contains(user)) {
			return true; 
		} 
		else {
			return false; 
        }		
	}
	
	/**
	 * This sets the total thinking time (minutes and seconds) enforced for both players.
	 * 
	 * @param int mins;
	 * @param int secs; 
	 * @return void;
	 * 
	 * @author Ada Andrei
	 */
	
	public static void setTime(int mins, int secs) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		Time time1 = new Time(0, mins, secs);
		Time time2 = new Time(0, mins, secs);
		quoridor.getCurrentGame().getWhitePlayer().setRemainingTime(time1);
		quoridor.getCurrentGame().getBlackPlayer().setRemainingTime(time2); 
	}

	/**
	 *This is a method that gets all the username from the list of players. 
	 *
	 * @author Ada Andrei (260866279)
	 * @param String name
	 * @return List <TOPlayer>
	 *
	 */

	public static List<String> getUsernames() {
		LinkedList<String> usernames = new LinkedList<>();
		for (User username : QuoridorApplication.getQuoridor().getUsers()) { // iterate through the list and add
			usernames.add(username.getName());
		}
		return usernames;
	}
	
	/**
 	* 
 	* @author alixe delabrousse (260868412)
	* 
 	* @param List<TOWall> wallStock - list of walls remaining of the current player
 	* 
	* This method allows the player to grab one if its walls from its remaining walls pile.
 	* This method enables the use of RotateWall, DropWall, and MoveWall.
 	* 
 	* If the player has no more wall remaining, a notification will appear.
 	* If the player has walls remaining, one of them will disappear from the
 	* stock and be placed at the initial position on the board.
 	* 
 	* 
 	*/
	
	public static TOWall grabWall() {
		
		final Quoridor quoridor = QuoridorApplication.getQuoridor(); // get quoridor
		Game game = quoridor.getCurrentGame();// get current game from quoridor
		Player currentPlayer = getCurrentPlayer(); // get the player of the turn 
		TOPlayer toCurrentPlayer = getPlayerOfCurrentTurn(); // create associated transfer object
		List<Wall> walls= currentPlayer.getWalls(); //this gets the complete list of 10 walls
		
		Wall grabbedWall; // current grabbed wall (null if no more walls left on stock)
		TOWall toGrabbedWall;
		Tile initialTile = getTileFromRowAndColumn(INITIAL_ROW, INITIAL_COLUMN); // Tile at initial position
		if (toCurrentPlayer.getWallsRemaining() != 0) {

			grabbedWall = walls.get(toCurrentPlayer.getWallsRemaining()-1); // the grabbed wall is the last one in the list
			toCurrentPlayer.setWallInHand(true);
			toCurrentPlayer.setWallsRemaining(toCurrentPlayer.getWallsRemaining()-1); //Remove one wall from walls remaining count
			
			toGrabbedWall = fromWall(grabbedWall); // create transfer object wall from model Wall
			
			
			// create the new Wall Move
			WallMove wallMove = new WallMove(game.getMoves().size(), game.getMoves().size()/2, currentPlayer, initialTile, game, INITIAL_ORIENTATION, grabbedWall);
			game.addMove(wallMove);
			game.setWallMoveCandidate(wallMove); // Set current wall move
			game.addMove(wallMove);
			TOWallCandidate wallCandidate = createTOWallCandidateFromWallMove(wallMove); // create associated TO
			
			toCurrentPlayer.setWallCandidate(wallCandidate);
			toGrabbedWall.SetGrabbed(true);
			
			return toGrabbedWall; // return the current grabbed wall
		} else {
			
				toCurrentPlayer.setWallInHand(false); // the current player does not have any wall in hand
				toCurrentPlayer.setWallCandidate(null);
				throw new WallStockEmptyException("No more walls on stock");
			

		}
			
	}
	
	
	
	/**
	 * 
	 * @author alixe delabrousse (260868412)
	 * 
	 * @param String side - the side in which the player wishes to move the wall.
	 * 
	 * This methods is enabled by the method grabWall.
	 * This methods allows the user to move their wall candidate around on the board.
	 * If the wall is not on the edge of the board:
	 * 		You can move the wall either "up", "down", "left" or "right".
	 * If the wall is on one edge of the board:
	 * 		You cannot move further in that direction.
	 * Each time the wall moves, new wall candidates (wall moves) are created
	 * at the positions the wall is allowed to move to (at its left, right, above or below)
	 * 
	 * 
	 */
	
	public static TOWallCandidate moveWall(String side) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();

		WallMove wallMove = game.getWallMoveCandidate();
		TOWallCandidate wallCandidate = getWallCandidate();

		Tile targetTile;
		
//		if(side.equals("up")) {
//			if (wallCandidate.getRow() == 8) {
//				throw new InvalidPositionException("Invalid move, already at edge");
//			} else {
//				targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
//				wallMove.setTargetTile(targetTile);
//				wallCandidate.setColumn(targetTile.getColumn());
//				wallCandidate.setRow(targetTile.getRow());
//			}
//		} else if (side.equals("down")) {
//			if (wallCandidate.getRow() == 1) {
//				throw new InvalidPositionException("Invalid move, already at edge");
//			} else {
//				targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
//				wallMove.setTargetTile(targetTile);
//				wallCandidate.setColumn(targetTile.getColumn());
//				wallCandidate.setRow(targetTile.getRow());
//			}
//		} else if (side.equals("right")) {
//			if (wallCandidate.getColumn() == 8) {
//				throw new InvalidPositionException("Invalid move, already at edge");
//				
//			} else {
//				targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
//				wallMove.setTargetTile(targetTile);
//				wallCandidate.setColumn(targetTile.getColumn());
//				wallCandidate.setRow(targetTile.getRow());
//			}
//		} else if (side.equals("left")) {
//			if (wallCandidate.getColumn() == 1) {
//				throw new InvalidPositionException("Invalid move, already at edge");
//			} else {
//				targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
//				wallMove.setTargetTile(targetTile);
//				wallCandidate.setColumn(targetTile.getColumn());
//				wallCandidate.setRow(targetTile.getRow());
//			}
//		} else {
//			throw new InvalidPositionException("invalid move");
//		} 
//		
//		return wallCandidate;
		
		if (side.equals("up") || side.equals("down") || side.equals("left") || side.equals("right")) {			
			if (wallCandidate.getOrientation() == Orientation.VERTICAL) {
				if (wallCandidate.getRow() < 8 && wallCandidate.getRow() > 1) {
					if (wallCandidate.getColumn() < 8 && wallCandidate.getColumn() > 1) {
						if (side.equals("up")) {
							
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
							
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 1) {
						if (side.equals("up")) {
							
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
							
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
						wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							throw new InvalidPositionException("invalid move");
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
						wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 8) {
						if (side.equals("up")) {
							
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
							
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							throw new InvalidPositionException("invalid move");
						}
					} else {
						throw new InvalidPositionException("invalid position");
					}
					
				} else if (wallCandidate.getRow() == 8) {
					if (wallMove.getTargetTile().getColumn() > 1 && wallMove.getTargetTile().getColumn() < 8) {
						if (side.equals("up")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 1) {
						if (side.equals("up")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 8) {
						if (side.equals("up")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("right")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
					}
					}
				} else if (wallCandidate.getRow() == 1) {
					if (wallMove.getTargetTile().getColumn() > 1 && wallMove.getTargetTile().getColumn() < 8) {
						if (side.equals("down")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("up")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
						wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 1) {
						if (side.equals("down")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							throw new InvalidPositionException("Illegal move");
					} else if (side.equals("up")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
					} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 8) {
						if (side.equals("down")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("right")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("up")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					}
				}
			} else if (wallCandidate.getOrientation() == Orientation.HORIZONTAL) {
				if (wallCandidate.getRow() < 8 && wallCandidate.getRow() > 1) {
					if (wallCandidate.getColumn() < 8 && wallCandidate.getColumn() > 1) {
						if (side.equals("up")) {
							
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
							
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} 
					} else if (wallCandidate.getColumn() == 1) {
						if (side.equals("up")) {
							
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
							
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							throw new InvalidPositionException("invalid move");
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 8) {
						if (side.equals("up")) {
							
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
							
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							throw new InvalidPositionException("invalid move");
						}
					} else {
						throw new InvalidPositionException("Invalid position");
					}
					
				} else if (wallCandidate.getRow() == 8) {
					if (wallCandidate.getColumn() > 1 && wallCandidate.getColumn() < 8) {
						if (side.equals("up")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 1) {
						if (side.equals("up")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 8) {
						if (side.equals("up")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("right")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("down")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()-1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					}
				} else if (wallCandidate.getRow() == 1) {
					if (wallMove.getTargetTile().getColumn() > 1 && wallMove.getTargetTile().getColumn() < 8) {
						if (side.equals("down")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("up")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 1) {
						if (side.equals("down")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("left")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("up")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("right")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()+1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					} else if (wallCandidate.getColumn() == 8) {
						if (side.equals("down")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("right")) {
							throw new InvalidPositionException("Illegal move");
						} else if (side.equals("up")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow()+1, wallMove.getTargetTile().getColumn());
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						} else if (side.equals("left")) {
							targetTile = getTileFromRowAndColumn(wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn()-1);
							wallMove.setTargetTile(targetTile);
							wallCandidate.setColumn(targetTile.getColumn());
							wallCandidate.setRow(targetTile.getRow());
						}
					}
				}
			} else {
				throw new InvalidPositionException("invalid position");
			}
			
		
		} else {
			throw new InvalidPositionException("Illegal Move");
		}
		
		return wallCandidate;
	}
	
	
	
	/**
	 * @author alixe delabrousse
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	
	public static Tile getTileFromRowAndColumn(int row, int column) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = quoridor.getBoard();
		
		int tileIndex = (row - 1)*9 + (column - 1);
		return board.getTile(tileIndex);
		
	}
	
	/**
	 * Returns a boolean indicating if the wall move has been made
	 * or not.
	 *
	 * @param row The row of the wall.
	 * @param column The column of the wall
	 * @param orientation Orientation of the wall
	 * @return true if the wall move is finalized, false otherwise
	 *
	 * @author Mohamed Mohamed
	 */
	public static boolean checkLastWallMove(int row, int column, Orientation orientation) {
		
		Game game=null;
		if(QuoridorApplication.getQuoridor().getCurrentGame()!=null) { //if the game exists reset the game to the current game
			game=QuoridorApplication.getQuoridor().getCurrentGame();
		}
		
		Move currentWallMove=null;
		
		//there will be a case where a user tries to place a wall but there will
		//no wall placed so he is not allowed
		//bc the move is only added to the list if it is a valid move.
		if(game.numberOfMoves()==0) {
			return false;
		}
		
		if(game.getMove(game.numberOfMoves()-1)!=null) {
			currentWallMove=game.getMove(game.numberOfMoves()-1);
		}
		
		
	//	Tile checkTile=new Tile(row, column, QuoridorApplication.getQuoridor().getBoard());
//		currentWallMove.getTargetTile();
		
		Direction direction=null;
		if (orientation.equals(orientation.HORIZONTAL)) {
			direction= direction.Horizontal;
		}else {
			direction= direction.Vertical;
		}
		
		int curRow=game.getMove(game.numberOfMoves()-1).getTargetTile().getRow();
		int curCol=game.getMove(game.numberOfMoves()-1).getTargetTile().getColumn();
		Direction curDirection=((WallMove) game.getMove(game.numberOfMoves()-1)).getWallDirection();
		
		
		if(curRow==row&& curCol==column && curDirection.equals(direction)  ) {
			return true; //the wall has been placed if the current tile has the same 
			             //coordinates as the wall that is being placed
		}else {
			
			return false;
		}
	
	
	}
	
	/**
	 * 
	 * @author mohamed Mohamed
	 * 
	 * @param wallCandidate
	 * 
	 * This method allows you to rotate a wall that is already held and change it's orientation to horizontal or to vertical
	 * reset the rotation of the transfer object and the wall candidate
	 * 
	 */
	
	public static void rotateWall(TOWallCandidate wall) {
		//this method should change the direction of the candidate
		
		//ask alixe alixe are the wall candidates set to have the same orientation.
		
		wall.rotate(); //rotated the TO
		Game game=null;
		
		if(QuoridorApplication.getQuoridor().getCurrentGame()!=null) { //if the game exists reset the game to the current game
			game=QuoridorApplication.getQuoridor().getCurrentGame();
		}
		
		/*WallMove currentMove= game.getWallMoveCandidate();
		Direction currentDirection = currentMove.getWallDirection();
		Direction newDirection=null;
		if (currentDirection.equals(Direction.Horizontal)) {
			newDirection=Direction.Vertical;
		}else {
			newDirection=Direction.Horizontal;
		}
		currentMove.setWallDirection(newDirection);*/
	}
	
	/**
	 * 
	 * @author Mohamed Mohamed
	 * 
	 * @param toWall
	 * 
	 * This method allows you to drop the wall that is in the users hand.
	 * Internally what it does is take as a parameter TOobject 
	 * checks if the move is a valid move given by calling 'validateWallPlacement' if it is valid 
	 * resets the position of the wallMove AND adds it to the list of wallmoves of the player
	 * AND adds it to the board AND changes the currentPlayer
	 * @return 
	 * 
	 * 
	 */
	
	public static boolean dropWall(TOWall toWall) { //getting the information from the transfer object that has been modified.
		//this method will drop 

		int row= toWall.getRow();
		int column= toWall.getColumn();
		Orientation orientation= toWall.getOrientation();
		Game game=null;
		
		
		//there is no running game but should be given the game is running
		if(QuoridorApplication.getQuoridor().getCurrentGame()!=null) { //if the game exists reset the game to the current game
			game=QuoridorApplication.getQuoridor().getCurrentGame();
		}
		
		Board board=null;
		if(QuoridorApplication.getQuoridor().getBoard()!=null) { //if the board exists reset the board to the current board
			board=QuoridorApplication.getQuoridor().getBoard();
		}
		
		GamePosition gamePosition=null;
		if(game.getCurrentPosition()!=null) { //if the GamePosition exists reset the gamePosition to the current gamePosition
			gamePosition=game.getCurrentPosition();
		}
		
		//if the wall is invalid do not throw drop it
		
		if(toWall.getValidity()==false) {
			
			return false; //so do not drop the wall
		}
		
		//case where we do not know if the wall is valid or not and depends on previous circumstances.
		boolean isValid = validateWallPlacement(row, column, orientation); // this returns true if it is a valid wallmove.
		if (isValid==true) {
			//reset the position of the wallMove 
			WallMove currentMove= game.getWallMoveCandidate();
			currentMove.setTargetTile(board.getTile((row-1)*9 +column-1));
			if (orientation.equals(orientation.HORIZONTAL)) {
				currentMove.setWallDirection(Direction.Horizontal);
			}else {
				currentMove.setWallDirection(Direction.Vertical);
			}
			
			//throw new RuntimeException("Current move/ "+currentMove.getGame()+" ! "+game);
			 
			
			//currentMove.getPrevMove().setNextMove(currentMove);
			if(game.numberOfMoves()==1 || game.numberOfMoves()==0) { //this the first move or we do not have moves at all..
				
				//case where the number of moves is zero:
				//only when the test runners this will happen because the grab wall feature
				//adds to the number of moves but in this case if it is zero we'll just add the only move ourselves
				if (game.numberOfMoves()==0) {
				game.addMove(currentMove);
				currentMove.setGame(game);
					
					
				
				}else {
				//case where the number of moves is 1
				//if the number of moves is one than it is the case where the wall grabbed is the first wall to be ever placed 
				//as the first move in that case we do not want to add to the list of moves nor set the previous bc there is none.	
					
				}
				
			}else {
				Move prevMove= game.getMove(game.numberOfMoves()-2); //is the last move
				prevMove.setNextMove(currentMove); //links the moves
			}
			//add the wall to the board AND set the next player, but first we need to check who is the current player
			if(currentMove.getPlayer().hasGameAsBlack()) { // it's a black player
				gamePosition.addBlackWallsOnBoard(currentMove.getWallPlaced());//just a list
				switchCurrentPlayer();
			}else { // the player is black
				gamePosition.addWhiteWallsOnBoard(currentMove.getWallPlaced());
				switchCurrentPlayer();
			}
			//reset the TO to null and the current wall candidate
			toWall.resetWall();
			TOPlayer currentPlayer= getPlayerOfCurrentTurn();
			currentPlayer.setWallInHand(false);
			game.setWallMoveCandidate(null);
			return true;
			
		}else {
			//do nothing internally just display an error message
			return false;	
		}
		
	}
	
	/**
	 * Changes the player of the current round to the one of the specified
	 * color. If that the player of that color is already the player of the
	 * current round, this method does nothing.
	 *
	 * @param color Color of the specified player
	 *
	 * @author Group 9
	 */
	public static void updatePlayerOfCurrentRound(Color color) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to switch player when not in game");
		}

		final Game game = quoridor.getCurrentGame();

		final GamePosition oldState = game.getCurrentPosition();
		final Player oldPlayer = oldState.getPlayerToMove();
		final Player newPlayer = getModelPlayerByColor(color);

		if (oldPlayer != newPlayer) {
			// Stop the clock of the current player
			stopClockForPlayer(oldPlayer);

			// Change the player of the current position
			oldState.setPlayerToMove(newPlayer);
		}

		// Make sure the clock for this new player is running
		runClockForPlayer(newPlayer);
	}

	/**
	 * Switches the player-to-move to the next player.
	 * 
	 * This method is called when the player finishes his turn.
	 * 
	 * @author Group 9
	 */
	public static void switchCurrentPlayer() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to switch player when not in game");
		}

		final Game game = quoridor.getCurrentGame();
		
		// Stop the clock of the current player
		final GamePosition oldState = game.getCurrentPosition();
		final Player oldPlayer = oldState.getPlayerToMove();
		stopClockForPlayer(oldPlayer);

		// Clone the current game position but with playerToMove changed
		final GamePosition newState = deriveNextPosition(oldState);

		// Get the next player
		Player newPlayer = oldPlayer.getNextPlayer();
		if (newPlayer == null) {
			// In the case that the next-player attribute is not setup
			// we can still determine who goes next based on color
			if (oldPlayer.hasGameAsWhite()) {
				newPlayer = game.getBlackPlayer();
			} else {
				newPlayer = game.getWhitePlayer();
			}
		}

		newState.setPlayerToMove(newPlayer);

		// Make the new state the current state
		game.setCurrentPosition(newState);

		// Start the clock of this new player
		runClockForPlayer(newPlayer);
	}

	/**
	 * Derives another game position with the next id, same player positions.
	 *
	 * Think of it as a strange duplicate (except id is changed)
	 *
	 * @param pos Original game position
	 * 
	 * @return the derived game position
	 */
	private static GamePosition deriveNextPosition(GamePosition pos) {
		final int nextId = pos.getId() + 1;
		if (GamePosition.hasWithId(nextId)) {
			// We start anew
			GamePosition.getWithId(nextId).delete();
		}

		// Clone the PlayerPosition objects (multiplicities)
		final PlayerPosition whitePos = pos.getWhitePosition();
		final PlayerPosition whitePosCopy = new PlayerPosition(whitePos.getPlayer(), whitePos.getTile());

		final PlayerPosition blackPos = pos.getBlackPosition();
		final PlayerPosition blackPosCopy = new PlayerPosition(blackPos.getPlayer(), blackPos.getTile());

		final GamePosition derived = new GamePosition(nextId, whitePosCopy, blackPosCopy, pos.getPlayerToMove(), pos.getGame());

		for (Wall w : pos.getWhiteWallsInStock()) {
			derived.addWhiteWallsInStock(w);
		}

		for (Wall w : pos.getWhiteWallsOnBoard()) {
			derived.addWhiteWallsOnBoard(w);
		}

		for (Wall w : pos.getBlackWallsInStock()) {
			derived.addBlackWallsInStock(w);
		}

		for (Wall w : pos.getBlackWallsOnBoard()) {
			derived.addBlackWallsOnBoard(w);
		}

		return derived;
	}

	/**
	 * Validates all positions associated to the current game position
	 * 
	 * @return true if positions are all valid,
	 * 	       false if at least one of them is invalid
	 * 
	 * @author Group 9
	 */
	public static boolean validateCurrentGamePosition() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to validate game position when not in game");
		}
		
		return validateGamePosition(quoridor.getCurrentGame().getCurrentPosition());
	}
	
	/**
	 * Validates all positions associated to a particular game position
	 * 
	 * @param pos The game position being validated
	 * @return true if positions are all valid,
	 * 	       false if at least one of them is invalid
	 * 
	 * @author Group 9
	 */
	public static boolean validateGamePosition(GamePosition pos) {
		// GamePosition is only valid if both pawns
		// and walls are in valid positions
		try {
			return validatePawnsOnBoard(pos) && validateWallsOnBoard(pos);
		} catch (RuntimeException ex) {
			// If exception occurs, then position cannot be valid
			return false;
		}
	}

	/**
	 * Validates all pawn positions associated to a particular game position
	 * 
	 * @param pos The game position being validated
	 * @return true if pawns are all in valid positions,
	 * 	       false if at least one of them is invalid
	 * 
	 * @author Group 9
	 */
	private static boolean validatePawnsOnBoard(GamePosition pos) {
		final Tile blackTile = pos.getBlackPosition().getTile();
		final Tile whiteTile = pos.getWhitePosition().getTile();

		return blackTile.getRow() != whiteTile.getRow()
			|| blackTile.getColumn() != whiteTile.getColumn();
	}
	
	/**
	 * Validates all wall positions associated to a particular game position
	 * 
	 * @param pos The game position being validated
	 * @return true if walls are all in valid positions,
	 * 	       false if at least one of them is invalid
	 * 
	 * @author Group 9
	 */
	private static boolean validateWallsOnBoard(GamePosition pos) {
		// Board is 9-by-9
		final boolean[][] tileMap = new boolean[9][9];

		for (Wall w : pos.getWhiteWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (!checkWallMoveAgainstTileMap(w.getMove(), tileMap)) {
				return false;
			}
		}

		for (Wall w : pos.getBlackWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (!checkWallMoveAgainstTileMap(w.getMove(), tileMap)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if wall is on top of a tile that has already been occupied
	 * 
	 * @param move The move with wall placement information
	 * @param tileMap The tile map, cells will be toggled
	 * @return true if wall is in valid position, false otherwise
	 * 
	 * @author Group 9
	 */
	private static boolean checkWallMoveAgainstTileMap(WallMove move, boolean[][] tileMap) {
		final Direction dir = move.getWallDirection();
		final Tile tile = move.getTargetTile();
		
		// Change indexing from 1-based to 0-based
		final int row = tile.getRow() - 1;
		final int col = tile.getColumn() - 1;

		// These two lines check if a particular spot on the tile map
		// is already occupied (toggled). If it is, then wall is not
		// in a valid position, hence it returns false. Otherwise,
		// the particular spot will be toggled (as this wall now
		// occupies this spot)
		if (tileMap[row][col]) return false;
		tileMap[row][col] = true;
		
		// The Direction test looks counter intuitive because
		// the tile map actually stores the transpose of the
		// expected grid (which is why increments look inverted)
		switch (dir) {
			case Vertical:
				if (tileMap[row + 1][col]) return false;
				tileMap[row + 1][col] = true;
				break;
			case Horizontal:
				if (tileMap[row][col + 1]) return false;
				tileMap[row][col + 1] = true;
				break;
			default:
				throw new AssertionError("Unrecognized wall direction: " + dir);
		}

		return true;
	}

	/**
	 * Validates a placement of a pawn
	 *
	 * @param row Row of pawn
	 * @param column Column of pawn
	 * @return true if position is valid, false otherwise
	 *
	 * @author Group 9
	 */
	public static boolean validatePawnPlacement(final int row, final int column) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for pawn placement when not in game");
		}
		
		final Game game = quoridor.getCurrentGame();
		final GamePosition pos = game.getCurrentPosition();
		return validatePawnPlacement(pos, row, column);
	}

	/**
	 * Validates a placement of a pawn given a particular GamePosition
	 *
	 * @param gpos A specific GamePosition
	 * @param row Row of pawn in pawn coordinates
	 * @param col Column of pawn in pawn coordinates
	 * @return true if position is valid, false if not
	 *
	 * @author Group 9
	 */
	private static boolean validatePawnPlacement(GamePosition gpos, final int row, final int col) {
		// Position must be on the board for it to be potentially valid
		if (!isValidPawnCoordinate(row, col)) {
			return false;
		}

		// Check if either of the two existing pawns overlap with the new position
		final Tile whiteTile = gpos.getWhitePosition().getTile();
		final Tile blackTile = gpos.getBlackPosition().getTile();

		return !(whiteTile.getRow() == row && whiteTile.getColumn() == col)
			&& !(blackTile.getRow() == row && blackTile.getColumn() == col);
	}

	/**
	 * Checks if position is a valid pawn coordinate (if it is still on board)
	 * 
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if position is on board, false if not
	 * 
	 * @author Group 9
	 */
	private static boolean isValidPawnCoordinate(int row, int col) {
		return !(row < 1 || row > 9 || col < 1 || col > 9);
	}

	
	/**
	 * Validates a placement of a wall
	 *
	 * @param row Row of wall
	 * @param column Column of wall
	 * @param orientation Orientation of wall
	 * @return true if position is valid, false otherwise
	 *
	 * @author Group 9
	 */
	public static boolean validateWallPlacement(final int row, final int column, Orientation orientation) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for wall placement when not in game");
		}

		final Game game = quoridor.getCurrentGame();
		final GamePosition pos = game.getCurrentPosition();
		return validateWallPlacement(pos, row, column, orientation);
	}

	/**
	 * Validates a placement of a wall given a particular GamePosition
	 *
	 * @param gpos A specific GamePosition
	 * @param row Row of wall
	 * @param column Column of wall
	 * @param orientation Orientation of wall
	 * @return true if position is valid, false otherwise
	 *
	 * @author Group 9
	 */
	public static boolean validateWallPlacement(GamePosition gpos, final int row, final int column, Orientation orientation) {
		// If both of the tiles are out of the board, the placement must be invalid
		if (!isValidWallCoordinate(row, column)) {
			return false;
		}

		// Calculate the location of the 2nd tile occupied by wall
		final int t2Row;
		final int t2Col;
		switch (orientation) {
			case VERTICAL:
				t2Row = row + 1;
				t2Col = column;
				break;
			case HORIZONTAL:
				t2Row = row;
				t2Col = column + 1;
				break;
			default:
				throw new AssertionError("Unrecognized wall orientation: " + orientation);
		}

		// Check against pawn coordinate because near edge, walls
		// go one over the wall coordinate (which then will fail
		// the wall coordinate test despite being valid)
		if (!isValidPawnCoordinate(t2Row, t2Col)) {
			return false;
		}

		for (Wall w : gpos.getWhiteWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (wallMoveOverlapsWithPlacement(w.getMove(), row, column, t2Row, t2Col)) {
				return false;
			}
		}

		for (Wall w : gpos.getBlackWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (wallMoveOverlapsWithPlacement(w.getMove(), row, column, t2Row, t2Col)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if position is a valid wall coordinate (if it is still on board)
	 * 
	 * @param row Row in wall coordinates
	 * @param col Column in wall coordinates
	 * @return true if position is on board, false if not
	 * 
	 * @author Group 9
	 */
	private static boolean isValidWallCoordinate(int row, int col) {
		return !(row < 1 || row > 8 || col < 1 || col > 8);
	}

	/**
	 * Checks if wall move overlaps with a wall placement
	 * 
	 * @param move Wall move being tested
	 * @param t1Row Row of first tile of wall placement
	 * @param t1Col Column of first tile of wall placement
	 * @param t2Row Row of second tile of wall placement
	 * @param t2Col Column of second tile of wall placement
	 * @return true if overlaps, false if no overlap
	 * 
	 * @author Group 9
	 */
	private static boolean wallMoveOverlapsWithPlacement(WallMove move, final int t1Row, final int t1Col, final int t2Row, final int t2Col) {
		final Direction dir = move.getWallDirection();
		final Tile tile = move.getTargetTile();

		// Compute first tile
		int row = tile.getRow();
		int col = tile.getColumn();

		if ((row == t1Row && col == t1Col) || (row == t2Row && col == t2Col)) {
			// Overlapping with one of the tiles happened
			return true;
		}
		
		// Compute second tile
		switch (dir) {
			case Vertical:      ++row; break;
			case Horizontal:    ++col; break;
			default:
			throw new AssertionError("Unrecognized wall direction: " + dir);
		}
		
		return (row == t1Row && col == t1Col)
			|| (row == t2Row && col == t2Col);
	}

	/**
	 * Saves the current board to a file
	 * 
	 * @param filePath The file being saved to
	 * @param overwriteIfExists Existing file will only be overwritten if true
	 * @return false if we do not overwrite, true if save operation succeeds
	 * @throws IOException If writing operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */

	public static boolean savePosition(String filePath, boolean overwriteIfExists) throws IOException {
		final File file = new File(filePath);
		if (file.exists() && !overwriteIfExists) {
			// File exists but user does not want to
			// overwrite the file, so we are done
			return false;
		}
		
		try (final Writer writer = new FileWriter(file)) {
			savePosition(writer);
		}
		return true;
	}

	/**
	 * Writes out the current board
	 *
	 * Note: this method does not close any streams; it is the caller's
	 * responsibility to do so.
	 * 
	 * @param destination The stream we are writing to
	 * @throws IOException If writing operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void savePosition(Writer destination) throws IOException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// Nothing to save, we are done
			return;
		}

		final PrintWriter pw = new PrintWriter(destination);
		final Game game = quoridor.getCurrentGame();

		final GamePosition position = game.getCurrentPosition();

		final boolean whitePlayerStart = position.getPlayerToMove().hasGameAsWhite();
		final StringBuilder whitePlayerMoves = new StringBuilder("W: ");
		final StringBuilder blackPlayerMoves = new StringBuilder("B: ");

		// Save the player's position
		whitePlayerMoves.append(tileToStr(position.getWhitePosition().getTile()));
		blackPlayerMoves.append(tileToStr(position.getBlackPosition().getTile()));

		// Save the walls on board
		for (Wall w : position.getWhiteWallsOnBoard()) {
			final WallMove move = w.getMove();
			whitePlayerMoves.append(", ").append(tileToStr(move.getTargetTile()));
			switch (move.getWallDirection()) {
				case Vertical:   whitePlayerMoves.append('v'); break;
				case Horizontal: whitePlayerMoves.append('h'); break;
					default:
					throw new AssertionError("Unhandled wall direction: " + move.getWallDirection());
		}
	}

		for (Wall w : position.getBlackWallsOnBoard()) {
			final WallMove move = w.getMove();
			blackPlayerMoves.append(", ").append(tileToStr(move.getTargetTile()));
			switch (move.getWallDirection()) {
				case Vertical:   blackPlayerMoves.append('v'); break;
				case Horizontal: blackPlayerMoves.append('h'); break;
				default:
					throw new AssertionError("Unhandled wall direction: " + move.getWallDirection());
	}
		}

		if (whitePlayerStart) {
			pw.println(whitePlayerMoves);
			pw.println(blackPlayerMoves);
		} else {
			pw.println(blackPlayerMoves);
			pw.println(whitePlayerMoves);
		}
	}
	
	/**
	 * Converts a tile to its equivalent saved form which is column as a-i
	 * followed by row as 1-9
	 * 
	 * @param tile The tile
	 * @return String form, null if the tile is null
	 */
	private static String tileToStr(final Tile tile) {
		if (tile == null) {
			return null;
		}

		return Character.toString((char) (tile.getColumn() + ('a' - 1))) + "" + tile.getRow();
	}

	/**
	 * Loads a previously saved board from a file 
	 * 
	 * @param filePath The file being read
	 * @throws IOException If reading operation fails 
	 * @throws InvalidLoadException If file cannot be processed
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void loadPosition(String filePath) throws IOException, InvalidLoadException {
		try (final Reader reader = new FileReader(filePath)) {
			loadPosition(reader);
		}
	}
	
	/**
	 * Reads in a previously saved board
	 *
	 * Note: this method does not close any streams; it is the caller's
	 * responsibility to do so.
	 * 
	 * @param source The stream we are reading from
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If stream cannot be processed
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void loadPosition(Reader source) throws IOException, InvalidLoadException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		
		final BufferedReader br = new BufferedReader(source);

		// Note: (Slightly counter intuitive)
		//   The stepDefs does create the players, but there is no way for us
		//   to retrieve the players. Also player has some very specific
		//   information such as thinking time, yet it is not saved.

		final Player whitePlayer = new Player(new Time(0, 3, 0), quoridor.getUser(0), 9, Direction.Horizontal);
		final Player blackPlayer = new Player(new Time(0, 3, 0), quoridor.getUser(1), 1, Direction.Horizontal);
		whitePlayer.setNextPlayer(blackPlayer);
		blackPlayer.setNextPlayer(whitePlayer);

		// Give our player some walls, they deserve it!
		for (int i = 0; i < 10; ++i) {
			if (Wall.hasWithId(i)) {
				whitePlayer.addWall(Wall.getWithId(i));
			} else {
				whitePlayer.addWall(i);
			}
		}

		for (int i = 10; i < 20; ++i) {
			if (Wall.hasWithId(i)) {
				blackPlayer.addWall(Wall.getWithId(i));
			} else {
				blackPlayer.addWall(i);
			}
		}

		final Game game;
		if (!quoridor.hasCurrentGame()) {
			game = new Game(GameStatus.Running, MoveMode.PlayerMove, quoridor);
		} else {
			game = quoridor.getCurrentGame();
			game.setGameStatus(GameStatus.Running);
			game.setMoveMode(MoveMode.PlayerMove);
		}
		game.setWhitePlayer(whitePlayer);
		game.setBlackPlayer(blackPlayer);

		final GamePosition initialPosition;

		// Meaningfull moves only start at index 1 (which
		// actually matches up with the corresponding round number)
		final String[] whitePlayerMoves;
		final String[] blackPlayerMoves;

		{
			// This is the only reading we actually need to do
			final String line1 = br.readLine();
			final String line2 = br.readLine();
			final boolean whiteStarts;

			// Check which player goes first
			switch (line1.charAt(0)) {
				case 'W':
					// Sanity check line2 must start with 'B'
					if (line2.charAt(0) != 'B') {
						throw new InvalidLoadException("Bad player color specification: W -> " + line2.charAt(0));
					}
					whiteStarts = true;
					initialPosition = createInitialGamePosition(whitePlayer, blackPlayer, whitePlayer, game);
					break;
				case 'B':
					// Sanity check line2 must start with 'W'
					if (line2.charAt(0) != 'W') {
						throw new InvalidLoadException("Bad player color specification: B -> " + line2.charAt(0));
					}
					whiteStarts = false;
					initialPosition = createInitialGamePosition(whitePlayer, blackPlayer, blackPlayer, game);
					break;
				default:
					throw new InvalidLoadException("Bad player color specification: " + line1.charAt(0));
			}

			// Perform default splitting
			final String[] seq1 = line1.split("\\s*[:,]\\s*");
			final String[] seq2 = line2.split("\\s*[:,]\\s*");

			if (whiteStarts) {
				whitePlayerMoves = seq1;
				blackPlayerMoves = seq2;
			} else {
				whitePlayerMoves = seq2;
				blackPlayerMoves = seq1;
			}
		}

		final String whitePosition = whitePlayerMoves[1];
		if (!whitePosition.matches("^[a-i][1-9]$")) {
			throw new InvalidLoadException("Invalid player position format for white player: " + whitePosition);
		}
		final int whiteRow = whitePosition.charAt(1) - '1' + 1;
		final int whiteCol = whitePosition.charAt(0) - 'a' + 1;
		if (!isValidPawnCoordinate(whiteRow, whiteCol)) {
			throw new InvalidLoadException("Invalid player position for white player: " + whitePosition);
		}

		final String blackPosition = blackPlayerMoves[1];
		if (!blackPosition.matches("^[a-i][1-9]$")) {
			throw new InvalidLoadException("Invalid player position format for black player: " + blackPosition);
		}
		final int blackRow = blackPosition.charAt(1) - '1' + 1;
		final int blackCol = blackPosition.charAt(0) - 'a' + 1;
		if (!isValidPawnCoordinate(blackRow, blackCol)) {
			throw new InvalidLoadException("Invalid player position for black player: " + blackPosition);
		}

		// check if these two pawn positions overlap
		if (whiteRow == blackRow && whiteCol == blackCol) {
			throw new InvalidLoadException("Loading pawn positions that overlap!");
		}

		//  Set the pawn position tile
		initialPosition.getWhitePosition().setTile(quoridor.getBoard().getTile((whiteRow - 1) * 9 + (whiteCol - 1)));
		initialPosition.getBlackPosition().setTile(quoridor.getBoard().getTile((blackRow - 1) * 9 + (blackCol - 1)));

		// Walls start at index 2
		for (int i = 2; i < whitePlayerMoves.length; ++i) {
			final String move = whitePlayerMoves[i];
			if (!move.matches("^[a-h][1-8][vh]$")) {
				throw new InvalidLoadException("Invalid position format for white wall: " + move);
			}

			final int row = move.charAt(1) - '1' + 1;
			final int col = move.charAt(0) - 'a' + 1;
			final Direction dir;
			switch(move.charAt(2)) {
				case 'v': dir = Direction.Vertical; break;
				case 'h': dir = Direction.Horizontal; break;
				default:  throw new InvalidLoadException("Invalid direction format for white wall: " + move.charAt('2'));
			}

			if (!initialPosition.hasWhiteWallsInStock()) {
				throw new InvalidLoadException("White trying to wall move without any walls available");
			}

			if (!validateWallPlacement(initialPosition, row, col, fromDirection(dir))) {
				throw new InvalidLoadException("Invalid placement for white wall: " + move);
			}

			final Wall wall = initialPosition.getWhiteWallsInStock(0);
			initialPosition.removeWhiteWallsInStock(wall);
			initialPosition.addWhiteWallsOnBoard(wall);

			final Tile tile = quoridor.getBoard().getTile((row - 1) * 9 + (col - 1));
			new WallMove(0, 0, whitePlayer, tile, game, dir, wall);
		}

		// See above logic
		for (int i = 2; i < blackPlayerMoves.length; ++i) {
			final String move = blackPlayerMoves[i];
			if (!move.matches("^[a-h][1-8][vh]$")) {
				throw new InvalidLoadException("Invalid position format for black wall: " + move);
			}

			final int row = move.charAt(1) - '1' + 1;
			final int col = move.charAt(0) - 'a' + 1;
			final Direction dir;
			switch(move.charAt(2)) {
				case 'v': dir = Direction.Vertical; break;
				case 'h': dir = Direction.Horizontal; break;
				default:  throw new InvalidLoadException("Invalid direction format for black wall: " + move.charAt('2'));
			}

			if (!initialPosition.hasBlackWallsInStock()) {
				throw new InvalidLoadException("black trying to wall move without any walls available");
			}

			if (!validateWallPlacement(initialPosition, row, col, fromDirection(dir))) {
				throw new InvalidLoadException("Invalid placement for black wall: " + move);
			}

			final Wall wall = initialPosition.getBlackWallsInStock(0);
			initialPosition.removeBlackWallsInStock(wall);
			initialPosition.addBlackWallsOnBoard(wall);

			final Tile tile = quoridor.getBoard().getTile((row - 1) * 9 + (col - 1));
			new WallMove(0, 0, blackPlayer, tile, game, dir, wall);
		}

		// We reach here, meaning position is all good!
		// final sanity check
		if (!validateGamePosition(initialPosition)) {
			throw new InvalidLoadException("Loaded game position is somehow invalid...");
		}

		game.setWallMoveCandidate(null);
		game.setCurrentPosition(initialPosition);
	}

	/**
	 * Creates or resuses a game position with id=0 that has white/black
	 * player in their initial position and all walls in stock.
	 * 
	 * Note: This does not set the game position as current
	 *
	 * @param whitePlayer White player
	 * @param blackPlayer Black player
	 * @param startingPlayer Starting player
	 * @param game Associated game
	 * @return game position with id=0
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static GamePosition createInitialGamePosition(Player whitePlayer, Player blackPlayer, Player startingPlayer, Game game) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();

		// Tile numbers are from CucumberStepDefinitions
		final PlayerPosition initialWhitePosition = new PlayerPosition(whitePlayer, quoridor.getBoard().getTile(36));
		final PlayerPosition initialBlackPosition = new PlayerPosition(blackPlayer, quoridor.getBoard().getTile(44));

		if (GamePosition.hasWithId(0)) {
			// Deletes the existing game position with id=0
			GamePosition.getWithId(0).delete();
		}

		// Create a new game position with id=0
		final GamePosition initialPosition = new GamePosition(0, initialWhitePosition, initialBlackPosition, startingPlayer, game);

		// Ensure all walls are in stock
		for (Wall w : whitePlayer.getWalls()) {
			initialPosition.removeWhiteWallsOnBoard(w);
			initialPosition.addWhiteWallsInStock(w);
		}
		
		for (Wall w : blackPlayer.getWalls()) {
			initialPosition.removeBlackWallsOnBoard(w);
			initialPosition.addBlackWallsInStock(w);
		}

		// As a sanity check, make sure this position is actually valid...
		if (!validateGamePosition(initialPosition)) {
			// so, somehow, it is not valid... crash!
			throw new AssertionError("PLEASE FIX THIS INITIAL GAME POSITION SETUP CUZ IT AIN'T VALID!!");
		}

		return initialPosition;
	}

	/**
	 * Tries to play a pawn move:
	 * Tries to play a {@link QuoridorController#tryPlayStepMove(int, int, Player, Tile, GamePosition) step move} first.
	 * If failed, tries to play a  {@link QuoridorController#tryPlayJumpMove(int, int, Player, Tile, GamePosition) jump move}
	 * 
	 * @param moveNumber Move number of the move
	 * @param roundNumber Round number of the move
	 * @param currentPlayer Player of the move
	 * @param target Tile of the move
	 * @param gamePos holds pre-state of move, will hold post-state of move
	 * @return A move instance of move is legal, null if move is illegal
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Move tryPlayPawnMove(int moveNumber, int roundNumber, Player currentPlayer, Tile target, GamePosition gamePos) {
		final Move move = tryPlayStepMove(moveNumber, roundNumber, currentPlayer, target, gamePos);
		if (move != null) {
			return move;
			}

		return tryPlayJumpMove(moveNumber, roundNumber, currentPlayer, target, gamePos);
	}

	/**
	 * Tries to play a step move onto a game position
	 *
	 * @param moveNumber Move number of the move
	 * @param roundNumber Round number of the move
	 * @param currentPlayer Player of the move
	 * @param target Tile of the move
	 * @param gamePos holds pre-state of move, will hold post-state of move
	 * @return A StepMove instance of move is legal, null if move is illegal
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static StepMove tryPlayStepMove(int moveNumber, int roundNumber, Player currentPlayer, Tile target, GamePosition gamePos) {
		final int row = target.getRow();
		final int col = target.getColumn();
		if (!validatePawnPlacement(gamePos, row, col)) {
			// If the tile is already occupied, then, obviously, the move cannot be completed
			return null;
		}

		final boolean playerHasWhitePawn = currentPlayer.hasGameAsWhite();
		final PlayerPosition playerPos;
		if (playerHasWhitePawn) {
			playerPos = gamePos.getWhitePosition();
		} else {
			playerPos = gamePos.getBlackPosition();
		}

		// Valid movement for a step (not jumps) must be one of
		// (x+1, y), (x-1, y), (x, y+1), (x, y-1) where x = row, y = column
		final Tile currentPos = playerPos.getTile();
		final int deltaRow = row - currentPos.getRow();
		final int deltaCol = col - currentPos.getColumn();
		if (1 != Math.abs(deltaRow) + Math.abs(deltaCol)) {
			// sum of the movement distances in rows and columns do not add up to 1
			// move cannot be completed because player tried moving too much
			return null;
		}

		// Finally, player cannot be moving stepping through walls
		if (isWallBlockingMovementFromTile(deltaRow, deltaCol, currentPos, gamePos)) {
			return null;
		}

		if (playerHasWhitePawn) {
			gamePos.setWhitePosition(new PlayerPosition(currentPlayer, target));
		} else {
			gamePos.setBlackPosition(new PlayerPosition(currentPlayer, target));
		}

		final StepMove move = new StepMove(moveNumber, roundNumber, currentPlayer, target, gamePos.getGame());
		return move;
	}

	/**
	 * See this simplified board:
	 *
	 *   +---+---+   +---+---+
	 * 2 |   |   | 2 |   |   | Y IS INVERTED
	 *   +[>====]+   +[>====]+ where @ is the tile, [>====] is the wall
	 * 1 | @ |   | 1 |   | @ |
	 *   +---+---+   +---+---+
	 *     1   2       1   2
	 *    [fig.1]     [fig.2]
	 *
	 * Only these two scenarios will return true
	 *
	 * @param wall A wall that is potentially above a tile
	 * @param tile A tile that potentially has the specific wall above it
	 * @return true if wall is above tile, false otherwise
	 *
	 * @see QuoridorController#wallIsBelowTile(Wall, Tile) wallsIsBelowTile
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsAboveTile(Wall wall, Tile tile) {
		return wallIsAboveTile(wall, tile.getRow(), tile.getColumn());
	}

	/**
	 * Checks to see if there is a wall above the tile
	 * 
	 * @param wall A wall that is potentially above the tile
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is immediately above, false otherwise
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsAboveTile(Wall wall, final int row, final int col) {
		if (!wall.hasMove()) {
			// Wall is not on the board, so cannot be above any tile
			return false;
		}

		final WallMove move = wall.getMove();
		if (Direction.Horizontal != move.getWallDirection()) {
			// Only horizontal walls can be above a tile
			return false;
		}

		// See above diagram, the wall's tile is always on the left side
		// (the '>' side). For the wall to be above, either it has the
		// same tile location as '@' [fig.1] or as
		// ('@'.row, '@'.column - 1) [fig.2]
		final Tile wallTile = move.getTargetTile();
		return wallTile.getRow() == row
			&& (wallTile.getColumn() == col || wallTile.getColumn() == col - 1);
	}

	/**
	 * Checks if any wall is above a particular tile
	 *
	 * @param gamePos current board configuration
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is immediately above, false if not
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean anyWallAboveTile(GamePosition gamePos, final int row, final int col) {
		for (Wall w : gamePos.getWhiteWallsOnBoard()) {
			if (wallIsAboveTile(w, row, col)) {
				return true;
			}
		}

		for (Wall w : gamePos.getBlackWallsOnBoard()) {
			if (wallIsAboveTile(w, row, col)) {
				return true;
			}
		}

		return false;
	}

	/**
	 *
	 * @param wall A wall that is potentially below a tile
	 * @param tile A tile that potentially has the specific wall below it
	 * @return true if wall is below tile, false otherwise
	 *
	 * @see QuoridorController#wallIsAboveTile(Wall, Tile) wallsIsAboveTile
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsBelowTile(Wall wall, Tile tile) {
		return wallIsBelowTile(wall, tile.getRow(), tile.getColumn());
	}

	/**
	 * Checks to see if there is a wall below the tile
	 * 
	 * @param wall A wall that is potentially below the tile
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is immediately below, false otherwise
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsBelowTile(Wall wall, final int row, final int col) {
		if (!wall.hasMove()) {
			// Wall is not on the board, so cannot be below any tile
			return false;
		}

		final WallMove move = wall.getMove();
		if (Direction.Horizontal != move.getWallDirection()) {
			// Only horizontal walls can be below a tile
			return false;
		}

		// Same reasoning as wallIsAboveTile,
		// but, instead, the wall is one unit higher than the tile
		final Tile wallTile = move.getTargetTile();
		return wallTile.getRow() == row - 1
			&& (wallTile.getColumn() == col || wallTile.getColumn() == col - 1);
	}

	/**
	 * Checks if any wall is below a particular tile
	 *
	 * @param gamePos current board configuration
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is immediately below, false if not
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean anyWallBelowTile(GamePosition gamePos, final int row, final int col) {
		for (Wall w : gamePos.getWhiteWallsOnBoard()) {
			if (wallIsBelowTile(w, row, col)) {
				return true;
			}
		}

		for (Wall w : gamePos.getBlackWallsOnBoard()) {
			if (wallIsBelowTile(w, row, col)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * See this simplified board:
	 *
	 *   +---+    +---+
	 * 2 |   -  2 | @ -
	 *   +---v    +---v where @ is the tile, | > | is the wall
	 * 1 | @ -  1 |   -
	 *   +---+    +---+
	 *  [fig.1]  [fig.2]
	 *
	 * Only these two scenarios will return true
	 *
	 * @param wall A wall that is potentially on the right of a tile
	 * @param tile A tile that potentially has the specific wall on the right of it
	 * @return true if wall is on the right side of tile, false otherwise
	 *
	 * @see QuoridorController#wallIsLeftOfTile(Wall, Tile) wallsIsLeftOfTile
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsRightOfTile(Wall wall, Tile tile) {
		return wallIsRightOfTile(wall, tile.getRow(), tile.getColumn());
	}

	/**
	 * Checks to see if there is a wall on the right of the tile
	 *
	 * @param wall A wall that is potentially on the right of the tile
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is on the immediate right, false if not
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsRightOfTile(Wall wall, final int row, final int col) {
		if (!wall.hasMove()) {
			// Wall is not on the board, so cannot be on the right any tile
			return false;
		}

		final WallMove move = wall.getMove();
		if (Direction.Vertical != move.getWallDirection()) {
			// Only vertical walls can be on the right of a tile
			return false;
		}

		// See above diagram, the wall's tile is always on the bottom.
		// For the wall to be on the left, either it has the same tile
		// location as '@' [fig.1] or as ('@'.row - 1, '@'.column) [fig.2]
		final Tile wallTile = move.getTargetTile();
		return wallTile.getColumn() == col
			&& (wallTile.getRow() == row || wallTile.getRow() == row - 1);
	}

	/**
	 * Checks if any wall is on the right of a particular tile
	 *
	 * @param gamePos current board configuration
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is on the immediate right, false if not
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean anyWallRightOfTile(GamePosition gamePos, final int row, final int col) {
		for (Wall w : gamePos.getWhiteWallsOnBoard()) {
			if (wallIsRightOfTile(w, row, col)) {
				return true;
			}
		}

		for (Wall w : gamePos.getBlackWallsOnBoard()) {
			if (wallIsRightOfTile(w, row, col)) {
				return true;
			}
		}

		return false;
	}

	/**
	 *
	 * @param wall A wall that is potentially on the left of a tile
	 * @param tile A tile that potentially has the specific wall on the left of it
	 * @return true if wall is on the left side of tile, false otherwise
	 *
	 * @see QuoridorController#wallIsRightOfTile(Wall, Tile) wallIsRightOfTile
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsLeftOfTile(Wall wall, Tile tile) {
		return wallIsLeftOfTile(wall, tile.getRow(), tile.getColumn());
	}

	/**
	 * Checks to see if there is a wall on the left of the tile
	 *
	 * @param wall A wall that is potentially on the left of the tile
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is on the immediate left, false if not
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean wallIsLeftOfTile(Wall wall, int row, int col) {
		if (!wall.hasMove()) {
			// Wall is not on the board, so cannot be on the left any tile
			return false;
		}

		final WallMove move = wall.getMove();
		if (Direction.Vertical != move.getWallDirection()) {
			// Only vertical walls can be on the left of a tile
			return false;
		}

		// Same reasoning as wallIsRightOfTile,
		// but, instead, the wall is one unit less than the tile
		final Tile wallTile = move.getTargetTile();
		return wallTile.getColumn() == col - 1
			&& (wallTile.getRow() == row || wallTile.getRow() == row - 1);
	}

	/**
	 * Checks if any wall is on the left of a particular tile
	 *
	 * @param gamePos current board configuration
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 * @return true if wall is on the immediate left, false if not
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean anyWallLeftOfTile(GamePosition gamePos, final int row, final int col) {
		for (Wall w : gamePos.getWhiteWallsOnBoard()) {
			if (wallIsLeftOfTile(w, row, col)) {
				return true;
			}
		}

		for (Wall w : gamePos.getBlackWallsOnBoard()) {
			if (wallIsLeftOfTile(w, row, col)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Tries to play a jump move onto a game position
	 *
	 * @param moveNumber Move number of the move
	 * @param roundNumber Round number of the move
	 * @param currentPlayer Player of the move
	 * @param target Tile of the move
	 * @param gamePos holds pre-state of move, will hold post-state of move
	 * @return A JumpMove instance of move is legal, null if move is illegal
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static JumpMove tryPlayJumpMove(int moveNumber, int roundNumber, Player currentPlayer, Tile target, GamePosition gamePos) {
		final int row = target.getRow();
		final int col = target.getColumn();
		if (!validatePawnPlacement(gamePos, row, col)) {
			// If the tile is already occupied, then, obviously, the move cannot be completed
			return null;
		}

		final boolean playerHasWhitePawn = currentPlayer.hasGameAsWhite();
		final PlayerPosition playerPos;
		if (playerHasWhitePawn) {
			playerPos = gamePos.getWhitePosition();
		} else {
			playerPos = gamePos.getBlackPosition();
		}

		// Valid movement for a jumps (not steps) must be one of
		// - far Jumps: (x+2, y), (x-2, y), (x, y+2), (x, y-2)
		// - Lateral Shifts: (x+1, y+1), (x+1, y+1), (x-1, y+1), (x-1, y-1)
		final Tile currentPos = playerPos.getTile();
		final int deltaRow = row - currentPos.getRow();
		final int deltaCol = col - currentPos.getColumn();
		if (2 != Math.abs(deltaRow) + Math.abs(deltaCol)) {
			// In other words, the absolute value of the movements must add up to 2
			return null;
		}

		// Walls block all movements, so check that first
		if (isWallBlockingMovementFromTile(deltaRow, deltaCol, currentPos, gamePos)) {
			// Pawn cannot move from the current position with
			// the selected direction since blocked by wall
			return null;
		}

		if (Math.abs(deltaRow) == 2 || Math.abs(deltaCol) == 2) {
			// We are doing a far jump:
			// +---+---+---+    +---+---+---+
			// | @ | O |   | => |   | O | @ | '@' is the player jumping
			// +---+---+---+    +---+---+---+ 'O' is another player
			//   1   2   3        1   2   3

			final int testRow = currentPos.getRow() + deltaRow / 2;
			final int testCol = currentPos.getColumn() + deltaCol / 2;

			// Test if 'O' exists, it should
			if (validatePawnPlacement(gamePos, testRow, testCol)) {
				// If 'O' exists, it would not be a valid pawn placement
				// => move is invalid if we the placement is valid
				return null;
			}

			// Now make sure no walls are in between rows or columns
			// (in above example, none should be between columns 1, 2, 3)

			if (isWallBlockingMovementToTile(deltaRow, deltaCol, target, gamePos)) {
				return null;
			}
		} else {
			// We are doing a lateral jump
			// +---+---+---+    +---+---+---+
			// |   | X |   |    |   | X |   | '@' is the player jumping
			// +[>====]+---+    +[>====]+---+ 'O' and 'X' are another players
			// |   | O |   |    |   | O | @ | '[>====]' is a wall
			// +---+---+---+ => +---+---+---+
			// |   | @ | ? |    |   |   | ? | Note: 'O' and either 'X' or the
			// +---+---+---+    +---+---+---+ wall enough for lateral jumps
			//   1   2   3        1   2   3

			// Check if 'O' exists, notice that with a single
			// tile check it could also be a lateral jump due
			// a blockage on the '?' side

			final int currentRow = currentPos.getRow();
			final int currentCol = currentPos.getColumn();

			boolean canPerformJump = false;

			// Check if '?' exists
			if (!canPerformJump && !validatePawnPlacement(gamePos, currentRow, currentCol + deltaCol / 2)) {
				// There should be sth, be it a wall or pawn, behind '?'
				canPerformJump = !validatePawnPlacement(gamePos, currentRow, currentCol + deltaCol)
						|| (deltaCol < 0 && anyWallRightOfTile(gamePos, currentRow, currentCol + deltaCol))
						|| (deltaCol > 0 && anyWallLeftOfTile(gamePos, currentRow, currentCol + deltaCol));
			}

			// Check if 'O' exists
			if (!canPerformJump && !validatePawnPlacement(gamePos, currentRow + deltaRow / 2, currentCol)) {
				// There should be sth, be it a wall or pawn, behind 'O'
				canPerformJump = !validatePawnPlacement(gamePos, currentRow + deltaRow, currentCol)
						|| (deltaRow < 0 && anyWallAboveTile(gamePos, currentRow + deltaRow, currentCol))
						|| (deltaRow > 0 && anyWallBelowTile(gamePos, currentRow + deltaRow, currentCol));
			}

			// If after both searches and the jump
			// is still illegal, return null
			if (!canPerformJump) {
				return null;
			}
		}

		if (currentPlayer.hasGameAsWhite()) {
			gamePos.setWhitePosition(new PlayerPosition(currentPlayer, target));
		} else {
			gamePos.setBlackPosition(new PlayerPosition(currentPlayer, target));
		}

		final JumpMove move = new JumpMove(moveNumber, roundNumber, currentPlayer, target, gamePos.getGame());
		return move;
	}

	/**
	 * Checks if movement from a particular tile in a particular direction is
	 * being blocked by a wall
	 *
	 * @param deltaRow Relative movement by row
	 * @param deltaCol Relative movement by column
	 * @param src Movement from this tile
	 * @param gamePos Curreng board setup
	 * @return true if any wall is blocking movement from a tile,
	 *         false if not blocking
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean isWallBlockingMovementFromTile(final int deltaRow, final int deltaCol, Tile src, GamePosition gamePos) {
		for (Wall w : gamePos.getWhiteWallsOnBoard()) {
			// If moving down, cannot have wall below the current or above the target (y inverted)
			if (deltaRow < 0 && wallIsBelowTile(w, src)) return true;

			// If moving up, cannot have wall above the current or below the target (y inverted)
			if (deltaRow > 0 && wallIsAboveTile(w, src)) return true;

			// If moving left, cannot have wall on the left of current or on the right of target
			if (deltaCol < 0 && wallIsLeftOfTile(w, src)) return true;

			// If moving right, cannot have wall on the right of current or on the left of target
			if (deltaCol > 0 && wallIsRightOfTile(w, src)) return true;
		}

		for (Wall w : gamePos.getBlackWallsOnBoard()) {
			// See above explaination
			if (deltaRow < 0 && wallIsBelowTile(w, src)) return true;
			if (deltaRow > 0 && wallIsAboveTile(w, src)) return true;
			if (deltaCol < 0 && wallIsLeftOfTile(w, src)) return true;
			if (deltaCol > 0 && wallIsRightOfTile(w, src)) return true;
		}

		return false;
	}

	/**
	 * Checks if movement to a particular tile in a particular direction is
	 * being blocked by a wall
	 *
	 * @param deltaRow Relative movement by row
	 * @param deltaCol Relative movement by column
	 * @param dst Movement to this tile
	 * @param gamePos Curreng board setup
	 * @return true if any wall is blocking movement to a tile,
	 *         false if not blocking
	 *
	 * @author Paul Teng (260862906)
	 */
	private static boolean isWallBlockingMovementToTile(int deltaRow, int deltaCol, Tile dst, GamePosition gamePos) {
		return isWallBlockingMovementFromTile(-deltaRow, -deltaCol, dst, gamePos);
	}

	/**
	 * Tries to play a wall move onto a game position
	 *
	 * @param moveNumber Move number of the move
	 * @param roundNumber Round number of the move
	 * @param wall Wall of the move
	 * @param dir Direction of the wall
	 * @param target Tile of the move
	 * @param gamePos holds pre-state of move, will hold post-state of move
	 * @return A WallMove instance of move is legal, null if move is illegal
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static WallMove tryPlayWallMove(int moveNumber, int roundNumber, Wall wall, Direction dir, Tile target, GamePosition gamePos) {
		if (!validateWallPlacement(gamePos, target.getRow(), target.getColumn(), fromDirection(dir))) {
			// If the wall is already occupied, then, obviously, the move cannot be completed
			return null;
		}

		// XXX: A proper Quoridor game needs to check if paths are
		// blocked, and this implementation does not do that!

		final Player currentPlayer = wall.getOwner();
		if (currentPlayer.hasGameAsWhite()) {
			gamePos.removeWhiteWallsInStock(wall);
			gamePos.addWhiteWallsOnBoard(wall);
		} else {
			gamePos.removeBlackWallsInStock(wall);
			gamePos.addBlackWallsOnBoard(wall);
		}

		final WallMove move = new WallMove(moveNumber, roundNumber, currentPlayer, target, gamePos.getGame(), dir, wall);
		return move;
	}

	/**
	 * Converts a direction enum to an orientation enum
	 * 
	 * @param dir Direction
	 * @return Equivalent as Orientation
	 * 
	 * @author Group 9
	 */
	public static Orientation fromDirection(final Direction dir) {
		if (dir == null) {
			// I suppose a null direction can be well defined?
			return null;
		}

		switch (dir) {
			case Vertical:      return Orientation.VERTICAL;
			case Horizontal:    return Orientation.HORIZONTAL;
			default:
				throw new IllegalArgumentException("Unsupported conversion from direction: " + dir.name());
		}
	}

	/**
	 *
	 * @return the player associated with the current turn
	 *
	 * @author Paul Teng (260862906)
	 */
	public static TOPlayer getPlayerOfCurrentTurn() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			return null;
		}

		final Game game = quoridor.getCurrentGame();
		if (!game.hasCurrentPosition()) {
			return null;
		}

		final GamePosition pos = game.getCurrentPosition();
		return fromPlayer(pos.getPlayerToMove());
	}

	/**
	 *
	 * @param name The name of the desired player
	 * @return the player associated with the name, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static TOPlayer getPlayerByName(String name) {
		final Player p = getModelPlayerByName(name);
		if (p == null) {
			return null;
		}
		
		return fromPlayer(p);
	}
	
	/**
	 * 
	 * @param color The color of the desired player
	 * @return the player with the associated color, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static TOPlayer getPlayerByColor(Color color) {
		final Player player = getModelPlayerByColor(color);
		if (player == null) {
			// player does not exist
			return null;
		}

		return fromPlayer(player);
		}

	/**
	 * Converts a Player to TOPlayer
	 *
	 * @param p the Player
	 * @return the corresponding TOPlayer
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static TOPlayer fromPlayer(Player p) {
		final TOPlayer player = new TOPlayer();
		player.setUsername(p.getUser().getName());

		final Game g;
		final Color c;
		if (p.hasGameAsBlack()) {
			g = p.getGameAsBlack();
			player.setColor((c = Color.BLACK));
		} else {
			g = p.getGameAsWhite();
			player.setColor((c = Color.WHITE));
		}

		player.setRow(-1);
		player.setColumn(-1);
		player.setWallsRemaining(-1);

		if (g.hasCurrentPosition()) {
			final GamePosition pos = g.getCurrentPosition();
			Tile t = null;
			switch (c) {
				case BLACK:
					t = pos.getBlackPosition().getTile();
					player.setWallsRemaining(pos.numberOfBlackWallsInStock());
					break;
				case WHITE:
					t = pos.getWhitePosition().getTile();
					player.setWallsRemaining(pos.numberOfWhiteWallsInStock());
					break;
			}

			if (t != null) {
				player.setRow(t.getRow());
				player.setColumn(t.getColumn());
			}
		}

		player.setTimeRemaining(p.getRemainingTime());
		player.setWallInHand(g.hasWallMoveCandidate());

		return player;
	}

	/**
	 *
	 * @param color The color of the desired player
	 * @return the player with the color
	 *
	 * @author Paul Teng (260862906)
	 */
	private static Player getModelPlayerByColor(Color color) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// There isn't even a game!
			return null;
		}

		final Game game = quoridor.getCurrentGame();
		switch (color) {
			case WHITE: return game.getWhitePlayer();
			case BLACK: return game.getBlackPlayer();
			default:    return null;
		}
	}

	/**
	 *
	 * @param name The name of the desired player
	 * @return the player associated with the name, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	private static Player getModelPlayerByName(String name) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// There isn't even a game!
			return null;
		}

		final Game game = quoridor.getCurrentGame();

		Player aPlayer = game.getWhitePlayer();
		if (aPlayer.getUser().getName().equals(name)) {
			return aPlayer;
		}
		
		aPlayer = game.getBlackPlayer();
		if (aPlayer.getUser().getName().equals(name)) {
			return aPlayer;
		}

		return null;
	}
	
	/**
	 * @author alixe delabrousse
	 * 
	 * @return a new transfer object wall candidate (wall move)
	 * 
	 */
	
	public static TOWallCandidate placeWallCandidateAtInitialPosition() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		TOPlayer currentPlayer = fromPlayer(quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove());
		
		
		TOWallCandidate wallCandidate  = currentPlayer.getWallCandidate();
		
		
		if (wallCandidate == null) wallCandidate = new TOWallCandidate(Orientation.VERTICAL,1,1);
		
		return wallCandidate;
		
	}
	
	
	/**
	 * 
	 * @author alixe delabrousse 
	 * 
	 * 
	 * @param wallMove
	 * @return
	 */
	public static TOWallCandidate createTOWallCandidateFromWallMove(WallMove wallMove) {
		if (wallMove == null) {
			return null;
		}

		Orientation orientation = fromDirection(wallMove.getWallDirection());
		
		TOWallCandidate wallCandidate = new TOWallCandidate(orientation, wallMove.getTargetTile().getRow(), wallMove.getTargetTile().getColumn());
		return wallCandidate;
	}
	
	/**
	 * 
	 * @author alixe delabrousse
	 * 
	 * @param wallCandidate
	 * @param orientation
	 * @param row
	 * @param column
	 * @return
	 */
	public static TOWallCandidate moveTOWallCandidateAtPosition(TOWallCandidate wallCandidate, Orientation orientation, int row, int column) {
		wallCandidate.setColumn(column);
		wallCandidate.setRow(row);
		wallCandidate.setOrientation(orientation);
		
		return wallCandidate;
	}
	
	/**
	 * @author alixe delabrousse
	 * 
	 * @param direction
	 * @param row
	 * @param column
	 * @return
	 */
	
	public static WallMove moveWallCandidateAtPosition(WallMove wallMove, Direction direction, Tile targetTile) {
		wallMove.setTargetTile(targetTile);
		wallMove.setWallDirection(direction);
		
		return wallMove;
	}
	
	/**
	 * @author alixe delabrousse (260868412)
	 * 
	 * @return the current wall candidate, the current wall move
	 */
	public static TOWallCandidate getWallCandidate() {
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		
		return game == null ? null : createTOWallCandidateFromWallMove(game.getWallMoveCandidate());
	}

	/**
	 *
	 * @param name The name of the player who owns the walls
	 * @return the walls associated to the player, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static List<TOWall> getWallsOwnedByPlayer(String name) {
		final Player p = getModelPlayerByName(name);
		if (p == null) {
			return null;
		}
		
		return p.getWalls().stream()
				.map(QuoridorController::fromWall)
				.collect(Collectors.toList());
	}
	
	/**
	 *
	 * @param color The color of the player who owns the walls
	 * @return the walls associated to the player, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static List<TOWall> getWallsOwnedByPlayer(Color color) {
		final Player p = getModelPlayerByColor(color);
		if (p == null) {
			// player does not exist
			return null;
		}

		return p.getWalls().stream()
				.map(QuoridorController::fromWall)
				.collect(Collectors.toList());
	}

	/**
	 * Converts a Wall to TOWall
	 *
	 * @param wall the Wall
	 * @return the corresponding TOWall
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static TOWall fromWall(Wall wall) {
		final TOWall toWall = new TOWall();
		if (wall.hasMove()) {
			WallMove move = wall.getMove();
			switch (move.getWallDirection()) {
				case Horizontal:
					toWall.setOrientation(Orientation.HORIZONTAL);
					break;
				case Vertical:
					toWall.setOrientation(Orientation.VERTICAL);
					break;
			}

			final Tile tile = move.getTargetTile();
			toWall.setRow(tile.getRow());
			toWall.setColumn(tile.getColumn());

			if (move != null && move.getGame() != null) {
				toWall.SetGrabbed(move.getGame().getWallMoveCandidate() == move);
			}
		}
		return toWall;
	}
	
	/**
	 * 
	 * @param p - player
	 * @return
	 * 
	 * @author alixe delabrousse (260868412)
	 * 
	 */
	

	public static List<TOWall> getRemainingWallsOfCurrentPlayer(){
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		Player p = game.getCurrentPosition().getPlayerToMove();
		
		return p.getWalls().stream().map(QuoridorController::fromWall)
				.collect(Collectors.toList());
		
	}
	
	public static List<TOWall> getRemainingBlackWalls(GamePosition pos){
		
		return pos.getBlackWallsInStock().stream().map(QuoridorController::fromWall)
				.collect(Collectors.toList());
	}
	
	public static List<TOWall> getRemainingWhiteWalls(GamePosition pos){
		
		return pos.getWhiteWallsInStock().stream().map(QuoridorController::fromWall)
				.collect(Collectors.toList());
	}
	
	
	
	/**
	 * 
	 * @param color - prompts the color of the Pawn of which you want the number of walls
	 * 
	 * @return the number of walls in stock of player with the specified color, 
	 *         -1 if no such player exists
	 *
	 * @author Paul Teng (260862906) and Alixe Delabrousse (260868412)
	 */
	
	public static int getWallsInStockOfColoredPawn(Color color) {
		final TOPlayer p = getPlayerByColor(color);
		if (p == null) {
			return -1;
		}

		return p.getWallsRemaining();
	}
	
	/**
	 * Checks to see if the clock for a particular player is running
	 *
	 * @param color Color of the player
	 * @return true if clock is running for player with the specified color,
	 *         false if clock is not running or if no such player exists
	 *
	 * @author Group 9
	 */
	public static boolean clockIsRunningForPlayer(Color color) {
		final Player player = getModelPlayerByColor(color);
		if (player == null) {
			// player does not exist
			return false;
		}

		return PLAYER_CLOCK.containsKey(player);
	}

	/**
	 * Starts a clock for a player. Once started, the player's remaining time
	 * will decrease as time passes. If the player's clock has already been
	 * started, this call does nothing
	 *
	 * @param color Color of the player
	 *
	 * @author Group 9
	 */
	public static void runClockForPlayer(Color color) {
		runClockForPlayer(getModelPlayerByColor(color));
	}

	/**
	 * Starts a clock for a player. Once started, the player's remaining time
	 * will decrease as time passes. If the player's clock has already been
	 * started, this call does nothing
	 *
	 * @param player The player whose clock is starting
	 *
	 * @author Group 9
	 */
	private static void runClockForPlayer(Player player) {
		if (player == null) {
			// wut?
			return;
		}

		// Create a task that, on each tick,
		// decreases remaining time of player
		final TimerTask task = new TimerTask() {
			@Override
			public void run() {
				final Time remTime = player.getRemainingTime();
				if (remTime.getHours() > 0 || remTime.getMinutes() > 0 || remTime.getSeconds() > 0) {
					// Subtract time by milliseconds per tick:
					// getTime() works with milliseconds
					final Time newTime = new Time(Math.max(0, remTime.getTime() - TIME_PER_TICK_MS));

					// Update the remaining time
					player.setRemainingTime(newTime);
				}
			}
		};

		if (PLAYER_CLOCK.putIfAbsent(player, task) == null) {
			// This means a new mapping is created, in other words,
			// the task should be sent over to the global clock
			//
			// This task should start immediately (hence 0)
			GLOBAL_CLOCK.scheduleAtFixedRate(task, 0, TIME_PER_TICK_MS);
		}
	}

	/**
	 * Stops the clock for the player. If the player's clock is not running,
	 * then this method does nothing.
	 *
	 * @param color Color of the player
	 *
	 * @author Group 9
	 */
	public static void stopClockForPlayer(Color color) {
		stopClockForPlayer(getModelPlayerByColor(color));
	}

	/**
	 * Stops the clock for the player. If the player's clock is not running,
	 * then this method does nothing.
	 *
	 * @param player The player whose clock is stopping
	 *
	 * @author Group 9
	 */
	private static void stopClockForPlayer(Player player) {
		if (player == null) {
			// player does not exist
			return;
		}

		final TimerTask task = PLAYER_CLOCK.remove(player);
		if (task != null) {
			task.cancel();
		}
	}

	/**
	 * 
	 * @return the current wall grabbed by the player
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 */
	public static TOWall getCurrentGrabbedWall() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		try {
			return fromWall(game.getWallMoveCandidate().getWallPlaced());
		} catch (Exception e) {
			throw new NoGrabbedWallException("No wall has been grabbed");
		}
	}
	

	/**
	 * 
	 *
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 * @return TOPlayer - returns the player associated with the white pawn
	 */

	public static TOPlayer getWhitePlayer(){
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		return fromPlayer(quoridor.getCurrentGame().getWhitePlayer());
	}
	
	/**
	 * 
	 * @author alixe delabrousse
	 * 
	 * @return TOPlayer - returns the player associated with the black pawn
	 */
	public static TOPlayer getBlackPlayer()	{
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		return fromPlayer(quoridor.getCurrentGame().getBlackPlayer());
	}
	
	/**
	 * 
	 * @author alixe delabrousse
	 * 
	 * @return
	 */
	
	public static Player getCurrentPlayer() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		
		if(!quoridor.hasCurrentGame()) return null;
		final Game game = quoridor.getCurrentGame();
		if (!game.hasCurrentPosition()) return null;
		
		final GamePosition pos = game.getCurrentPosition();
		return pos.getPlayerToMove();
		
	}
	


	/**
	 * Returns a list of white walls on board
	 * 
	 * @return a list of white walls on board, null if no game
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static List<TOWall> getWhiteWallsOnBoard() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			return null;
		}
		final Game game = quoridor.getCurrentGame();
		if (!game.hasCurrentPosition()) {
			return null;
		}

		return game.getCurrentPosition().getWhiteWallsOnBoard().stream().map(QuoridorController::fromWall)
				.collect(Collectors.toList());
	}

	/**
	 * Returns a list of black walls on board
	 * 
	 * @return a list of black walls on board, null if no game
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static List<TOWall> getBlackWallsOnBoard() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			return null;
		}
		final Game game = quoridor.getCurrentGame();
		if (!game.hasCurrentPosition()) {
			return null;
		}

		return game.getCurrentPosition().getBlackWallsOnBoard().stream().map(QuoridorController::fromWall)
				.collect(Collectors.toList());
	}

}// end QuoridorController
