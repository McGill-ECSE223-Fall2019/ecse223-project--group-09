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
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ca.mcgill.ecse223.quoridor.controller.path.Node;
import ca.mcgill.ecse223.quoridor.controller.path.PathFinder;
import ca.mcgill.ecse223.quoridor.controller.Color;
import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Destination;
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

	// ***** Load Game Regex *****

	// Group 1: move number, Group 2: white-player move, Group 3: black-player move
	private static final Pattern GAME_FILE_ACTION_FMT = Pattern.compile(
			"^(\\d+)\\.\\s*([a-h][1-8][vh]|[a-i][1-9])\\s+([a-h][1-8][vh]|[a-i][1-9])\\s*$");

	/**
	 *
	 * @author Barry Chen
	 *
	 * This method create a new empty game associated to the quoridor
	 */
	public static Game createGame() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		if(quoridor.hasCurrentGame()) {
			quoridor.getCurrentGame().delete();
		}
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
	public static void createPlayer() {

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
	 * This method starts a new game by the changing the game status from Initializing to ReadyToStart
	 *
	 */
	public static void startNewGame(){
		/*
		When A new game is being initialized
	    And White player chooses a username
	    And Black player chooses a username
	    And Total thinking time is set
	    Then The game shall become ready to start
		 */
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game aGame = quoridor.getCurrentGame();

		if(aGame.getGameStatus() == GameStatus.Initializing) {
			if((aGame.hasWhitePlayer())&&(aGame.hasBlackPlayer())){
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
	 * @author Barry Chung
	 *
	 */
	public static void StartClock() {

		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game aGame = quoridor.getCurrentGame();
		//checkout for game position

		//Given The game is ready to start
		if(aGame.getGameStatus() == GameStatus.ReadyToStart){
			//When I start the clock
			runClockForPlayer(aGame.getWhitePlayer());
			//Then The game shall be running
			aGame.setGameStatus(GameStatus.Running);
			//And The board shall be initialized

		}
	}


	/**
	 *
	 * @author Barry Chin
	 *
	 * This method creates an empty 9 * 9 board
	 *
	 */
	public static Board createNewBoard(){
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		if(quoridor.hasBoard()) {
			quoridor.getBoard().delete();
		}
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
	 * @author Berry Chen
	 *
	 * @param none
	 * @return initialized board
	 *
	 * This method initialize the board which place both players' pawn at its initial position and setting both
	 * player's wall stock
	 *
	 */

	public static void initiateBoard() {

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

		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game aGame = quoridor.getCurrentGame();

		//Given The game is ready to start
		if(aGame.getGameStatus() == GameStatus.ReadyToStart){

			Board gameBoard = createNewBoard();
			aGame.getQuoridor().setBoard(gameBoard);

			PlayerPosition blancPlayerInitialPosition = new PlayerPosition(aGame.getWhitePlayer(),new Tile(1,5,gameBoard));
			PlayerPosition noirPlayerInitialPosition = new PlayerPosition(aGame.getBlackPlayer(),new Tile(9,5,gameBoard));
			GamePosition newCurrentPosition = new GamePosition(1,blancPlayerInitialPosition,noirPlayerInitialPosition,aGame.getWhitePlayer(),aGame);

			//Then It shall be white player to move
			aGame.setCurrentPosition(newCurrentPosition);
			for(int i=1; i<=20; i++) {
				if (Wall.hasWithId(i)) {
					Wall.getWithId(i).delete();
				}

				if(i <= 10){
					//And All of White's walls shall be in stock
					aGame.getCurrentPosition().addWhiteWallsInStock(new Wall(i,aGame.getWhitePlayer()));
				}
				else{
					//And All of Black's walls shall be in stock
					aGame.getCurrentPosition().addBlackWallsInStock(new Wall(i,aGame.getBlackPlayer()));
				}
			}

		}


	}


	/**
	 * This method allows the user to create a new username.
	 *
	 * @param String user, Color COLOR
	 *
	 * @author Ada Andrei
	 */

	public static void createOrSelectUsername(String user, Color COLOR) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();
		if (COLOR == Color.WHITE) {
			if (!usernameExists(user))	{
			User anUser = quoridor.addUser(user);
			Player player = new Player(null, anUser, 9, Direction.Horizontal);
			game.setWhitePlayer(player);
			}
			else {
				User.getWithName(user);
				Player aPlayer = new Player(null,User.getWithName(user), 9, Direction.Horizontal);
				game.setWhitePlayer(aPlayer);
			}
		}
		else if (COLOR == Color.BLACK) {
			if (!usernameExists(user))	{
				User anUser = quoridor.addUser(user);
				Player player = new Player(null, anUser, 1, Direction.Horizontal);
				game.setBlackPlayer(player);
				}
			else {
				User.getWithName(user);
				Player aPlayer = new Player(null,User.getWithName(user), 1, Direction.Horizontal);
				game.setBlackPlayer(aPlayer);
			}
		}
	}

	/**
	 * This is the same method that throws an exception, when user tries to use an existing username.
	 * @param String user, Color COLOR
	 * @throws InvalidInputException
	 * @author Ada Andrei
	 */

	public static void createNewUsername (String user, Color COLOR) throws InvalidInputException {
			final Quoridor quoridor = QuoridorApplication.getQuoridor();
			Game game = quoridor.getCurrentGame();
			if (COLOR == Color.WHITE) {
				if (!usernameExists(user))	{
				User anUser = quoridor.addUser(user);
				Player player = new Player(null, anUser, 9, Direction.Horizontal);
				game.setWhitePlayer(player);
				}
				else {
					throw new InvalidInputException("This username already exists, please enter a new one or select the existing username.");
				}
			}
			else if (COLOR == Color.BLACK) {
				if (!usernameExists(user))	{
					User anUser = quoridor.addUser(user);
					Player player = new Player(null, anUser, 1, Direction.Horizontal);
					game.setBlackPlayer(player);
					}
				else {
					throw new InvalidInputException("This username already exists, please enter a new one or select the existing username.");
				}
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
		if (User.hasWithName(user)) {
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

		// Clone game position before removing wall from stock
		// (so wall count is not broken)
		GamePosition gpos = deriveNextPosition(game.getCurrentPosition());
		game.setCurrentPosition(gpos);

		Wall grabbedWall = null; // current grabbed wall (null if no more walls left on stock)
		TOWall toGrabbedWall;
		Tile initialTile = getTileFromRowAndColumn(INITIAL_ROW, INITIAL_COLUMN); // Tile at initial position
		if (toCurrentPlayer.getWallsRemaining() != 0) {

			int rnd = 0;
			// Grab the first wall of the stock, also need a hack for the test scenarios
			for (int i = 0; grabbedWall == null && i < toCurrentPlayer.getWallsRemaining(); ++i) {
				if (currentPlayer.hasGameAsWhite()) {
					grabbedWall = gpos.getWhiteWallsInStock(i);
					gpos.removeWhiteWallsInStock(grabbedWall);
					rnd = 1;
				} else {
					grabbedWall = gpos.getBlackWallsInStock(i);
					gpos.removeBlackWallsInStock(grabbedWall);
					rnd = 2;
				}
			}

			if (grabbedWall == null) {
				throw new IllegalStateException("Inconsistent model!!! WTF!!?");
			}

			toCurrentPlayer.setWallInHand(true);
			toCurrentPlayer.setWallsRemaining(toCurrentPlayer.getWallsRemaining()-1); //Remove one wall from walls remaining count

			toGrabbedWall = fromWall(grabbedWall); // create transfer object wall from model Wall


			// create the new Wall Move
			WallMove wallMove = new WallMove(game.getMoves().size() / 2 + 1, rnd, currentPlayer, initialTile, game, INITIAL_ORIENTATION, grabbedWall);
			game.setCurrentMove(wallMove);
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
		// *****	throw new InvalidPositionException("Illegal Move");
		}

		return wallCandidate;
	}

	/**
	 * moveWall method used by the view for the application to have a smoother feel.
	 *
	 * @author Alixe Delabrousse (260868412)
	 *
	 *
	 * @param row
	 * @param column
	 * @return
	 */


	public static TOWallCandidate moveWall(int row, int column) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();

		WallMove wallMove = game.getWallMoveCandidate();
		TOWallCandidate wallCandidate = getWallCandidate();

		Tile targetTile = getTileFromRowAndColumn(row, column);

		wallMove.setTargetTile(targetTile);
		wallCandidate.setColumn(targetTile.getColumn());
		wallCandidate.setRow(targetTile.getRow());

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


		wall.rotate(); //rotated the TO
		Game game=null;

		if(QuoridorApplication.getQuoridor().getCurrentGame()!=null) { //if the game exists reset the game to the current game
			game=QuoridorApplication.getQuoridor().getCurrentGame();
		}

		WallMove currentMove= game.getWallMoveCandidate();
		Direction currentDirection = currentMove.getWallDirection();
		Direction newDirection=null;
		if (currentDirection.equals(Direction.Horizontal)) {

			newDirection=Direction.Vertical;
		}else {
			newDirection=Direction.Horizontal;
		}
		currentMove.setWallDirection(newDirection);
	}



	/**
	 *
	 * @author Mohamed Mohamed
	 *
	 */
	public static boolean stepBackward(boolean isTesting) {
		

		Game game=null;

		if(QuoridorApplication.getQuoridor().getCurrentGame()!=null) { //if the game exists reset the game to the current game
			game=QuoridorApplication.getQuoridor().getCurrentGame();
		}

		if(!game.getGameStatus().equals(GameStatus.Replay)) {
			System.err.println("the game status is not in replay");
			return false;
		}else {

			GamePosition currentPosition= game.getCurrentPosition();



			int currPos = game.getPositions().indexOf(currentPosition);
			if (currPos==0) {
				return false;//already at the first position
			}
			
			GamePosition prevPosition= game.getPosition(currPos-1);
			//adjustin the number of walls.
				
//				int index=game.indexOfPosition(prevPosition);
//				if(index==0) {
//					if(prevPosition.getWhiteWallsInStock().size()==9) {
//						//add a wall
//						Wall wall= new Wall(1, game.getWhitePlayer());
//						prevPosition.addWhiteWallsInStock(wall);
//					}
//					
//					if(prevPosition.getBlackWallsInStock().size()==9) {
//				//add a wall
//					Wall wall= new Wall(1, game.getBlackPlayer());
//					prevPosition.addBlackWallsInStock(wall);
//				}
//				
//			}else {//this is not the first gameposition
//				GamePosition before=game.getPosition(currPos-2);
//				int numOfWallsNow=prevPosition.getWhiteWallsInStock().size();
//					int numOfWallsBef=before.getWhiteWallsInStock().size();
//					if (numOfWallsNow!=numOfWallsBef) {
//						Wall wall= new Wall(numOfWallsBef, game.getWhitePlayer());
//						//prevPosition.addWhiteWallsInStock(wall);
//				}
//				
//				int numOfBWallsNow=prevPosition.getWhiteWallsInStock().size();
//				int numOfBWallsBef=before.getWhiteWallsInStock().size();
//				if (numOfBWallsNow!=numOfBWallsBef) {
//					Wall wall= new Wall(numOfBWallsBef, game.getBlackPlayer());
//					prevPosition.addBlackWallsInStock(wall);
//					}					
//			}
				
			
			game.setCurrentPosition(prevPosition);
			
			Move currentMove= game.getCurrentMove();
			int currMove = game.getMoves().indexOf(currentMove);
			if(currMove==0) {//already at the first move
				//do nothing
			}else{
				game.setCurrentMove(game.getMove(currMove-1));
			}
		
		}
		return true;

	}

	/**
	 *
	 * @author Mohamed Mohamed
	 *
	 */
	public static boolean stepForward(boolean isTesting) {

		Game game=null;

		if(QuoridorApplication.getQuoridor().getCurrentGame()!=null) { //if the game exists reset the game to the current game
			game=QuoridorApplication.getQuoridor().getCurrentGame();
		}

		if(!game.getGameStatus().equals(GameStatus.Replay)) {
			return false;
		}else {


			GamePosition currentPosition= game.getCurrentPosition();
			int currPos=game.getPositions().indexOf(currentPosition);
			if(currPos>=game.getPositions().size()-1) {
				System.err.println("i came here so whatever ");
				return false;
			}
			GamePosition nextPos= game.getPosition(currPos+1);
			game.setCurrentPosition(nextPos);
			//game.setCurrentMove(game.getMove(currPos+1));


			Move currentMove= game.getCurrentMove();
			int currMove = game.getMoves().indexOf(currentMove);
			if(currMove>=game.getMoves().size()-1) {
				//do nothing
			}else{
				game.setCurrentMove(game.getMove(currMove+1));
			}

		}
		return true;

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
				game.setCurrentMove(currentMove);
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

		// Switch the player
		oldState.setPlayerToMove(newPlayer);

		// Check the game result, and if game status is changed, we are done
		if (initiateCheckGameResult()) {
			return;
		}

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
		final List<Wall> walls = new ArrayList<>();
		walls.addAll(pos.getWhiteWallsOnBoard());
		walls.addAll(pos.getBlackWallsOnBoard());

		for (int i = 0; i < walls.size() - 1; ++i) {
			final Wall w1 = walls.get(i);
			final Wall w2 = walls.get(i + 1);
			final WallMove move = w2.getMove();
			final Tile target = move.getTargetTile();
			if (wallMoveOverlapsWithPlacement(w1.getMove(), target.getRow(), target.getColumn(), fromDirection(move.getWallDirection()))) {
				return false;
			}
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
	/* package */ static boolean validatePawnPlacement(GamePosition gpos, final int row, final int col) {
		if (gpos.getGame().getGameStatus() != GameStatus.Running) {
			// pawn should not be movable by player
			return false;
		}

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
		if (!getWinnerForGame(gpos.getGame()).isEmpty()) {
			// Game already has a winner or it was a draw:
			// all moves are invalid since game ended
			return false;
		}

		// If both of the tiles are out of the board, the placement must be invalid
		if (!isValidWallCoordinate(row, column)) {
			return false;
		}

		for (Wall w : gpos.getWhiteWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (wallMoveOverlapsWithPlacement(w.getMove(), row, column, orientation)) {
				return false;
			}
		}

		for (Wall w : gpos.getBlackWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (wallMoveOverlapsWithPlacement(w.getMove(), row, column, orientation)) {
				return false;
			}
		}

		// Then finally we check if a path can be found!
		// (that is, for both players)
		final Node[][] nodeMap = createPathNodes(gpos);
		unlinkNodeWithWallMove(nodeMap, row, column, orientation);

		// Checks to see if all players have valid paths
		return EnumSet.allOf(Color.class).equals(getPlayersWithValidPaths(gpos, nodeMap));
	}

	/**
	 * Checks to see if each player has a path to their respected destination
	 *
	 * @param gpos    GamePosition
	 * @param nodeMap Node map
	 * @return a set of player's color whose paths are valid
	 *
	 * @author Paul Teng (260862906)
	 */
	private static EnumSet<Color> getPlayersWithValidPaths(final GamePosition gpos, final Node[][] nodeMap) {
		// // Comment the following out to see the node map (useful when debugging)
		// Node.debugPrint(System.err, nodeMap);

		// Assume all players have a valid path to the destination
		final EnumSet<Color> set = EnumSet.allOf(Color.class);
		// The color from the set if a path cannot be traced
		set.removeIf(color -> !createPathFinderForPlayer(gpos, color, nodeMap).trace());
		// And return whatever is left in that set
		return set;
	}

	/**
	 * Initiates the path existence test for all players given the current board
	 * snapshot and wall candidate
	 *
	 * @return a set of player's color whose paths are valid
	 *
	 * @author Paul Teng (260862906)
	 */
	public static EnumSet<Color> initiatePathExistenceTest() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// No Game No Path
			return EnumSet.noneOf(Color.class);
		}

		final Game game = quoridor.getCurrentGame();
		final GamePosition gpos = game.getCurrentPosition();

		// Create the graph
		final Node[][] nodeMap = createPathNodes(gpos);
		// Unlink (if necessary) based on wall move candidate
		if (game.hasWallMoveCandidate()) {
			final WallMove move = game.getWallMoveCandidate();
			final Tile t = move.getTargetTile();
			unlinkNodeWithWallMove(nodeMap, t.getRow(), t.getColumn(), fromDirection(move.getWallDirection()));
		}

		// Trace path!
		return getPlayersWithValidPaths(gpos, nodeMap);
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
	 * @param t1Row Row of wall
	 * @param t1Col Column of wall
	 * @param orientation Orientation of wall
	 * @return true if overlaps, false if no overlap
	 *
	 * @author Group 9
	 */
	private static boolean wallMoveOverlapsWithPlacement(WallMove move, final int t1Row, final int t1Col, Orientation orientation) {
		final Orientation dir = fromDirection(move.getWallDirection());
		final Tile tile = move.getTargetTile();

		// Compute first tile
		int row = tile.getRow();
		int col = tile.getColumn();

		if (row == t1Row && col == t1Col) {
			return true;
		}

		if (dir != orientation) {
			// Different direction, only way to overlap is by having same tile, which we already tested
			return false;
		}

		// Check for extension case
		if (dir == Orientation.VERTICAL) {
			return col == t1Col && Math.abs(row - t1Row) == 1;
		} else {
			return row == t1Row && Math.abs(col - t1Col) == 1;
		}
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
	 * Loads a previously saved game from a file
	 *
	 * @param filePath The file being read
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If file cannot be processed
	 *
	 * @author Group-9
	 */
	public static void loadGame(String filePath) throws IOException, InvalidLoadException {
		try (final Reader reader = new FileReader(filePath)) {
			loadGame(reader);
		}
	}

	/**
	 * Reads in a previously saved game
	 *
	 * Note: this method does not close any streams; it is the caller's
	 * responsibility to do so.
	 *
	 * @param source The stream we are reading from
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If stream cannot be processed
	 *
	 * @author Group-9
	 */
	public static void loadGame(Reader source) throws IOException, InvalidLoadException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		final BufferedReader br = new BufferedReader(source);

		// Note: (Slightly counter intuitive; taken directly from loadPosition)
		//   The stepDefs does create the players, but there is no way for us
		//   to retrieve the players. Also player has some very specific
		//   information such as thinking time, yet it is not saved.

		if (quoridor.numberOfUsers() < 2) {
			// Provide dummy users
			quoridor.addUser("User 1");
			quoridor.addUser("User 2");
		}

		QuoridorController.createGame();

		final Player whitePlayer = new Player(new Time(0, 3, 0), quoridor.getUser(0), 9, Direction.Horizontal);
		final Player blackPlayer = new Player(new Time(0, 3, 0), quoridor.getUser(1), 1, Direction.Horizontal);
		whitePlayer.setNextPlayer(blackPlayer);
		blackPlayer.setNextPlayer(whitePlayer);

		final Game game = quoridor.getCurrentGame();
		game.setWhitePlayer(whitePlayer);
		game.setBlackPlayer(blackPlayer);

		QuoridorController.startNewGame();
		QuoridorController.initiateBoard();
		QuoridorController.StartClock();

		// Then we just replay the whole game!
		int lastIdx = 0;
		String action;
		while ((action = br.readLine()) != null) {
			// In case anyone, myself included, cannot read the regex above,
			// the format of action is:
			// {1: int}. {2: pawn or wall coordinate} {3: pawn or wall coordinate}
			final Matcher matcher = GAME_FILE_ACTION_FMT.matcher(action);
			if (!matcher.matches()) {
				throw new InvalidLoadException("Illegal action in game save: `" + action + '`');
			}

			// Make sure move number is incrementing correctly
			final int moveNumber = Integer.parseInt(matcher.group(1));
			if (lastIdx + 1 != moveNumber) {
				throw new InvalidLoadException("Inconsistent move number count: " + action);
			}
			lastIdx = moveNumber;

			// Only continue if game is running
			// (if a winner is already determined, we ignore)
			if (game.getGameStatus() != GameStatus.Running) {
				break;
			}

			{
				// Play white player's move first
				final String wpMove = matcher.group(2);
				final int wpRow = wpMove.charAt(1) - '1' + 1;
				final int wpCol = wpMove.charAt(0) - 'a' + 1;
				if (wpMove.length() == 2) {
					// This is either a step or a jump move
					final PlayerPosition pos = game.getCurrentPosition().getWhitePosition();
					final Tile t = pos.getTile();

					final int drow = wpRow - t.getRow();
					final int dcol = wpCol - t.getColumn();

					boolean result = false;

					// Shamelessly taken from BoardWindow#onTileClicked(int, int)

					// Steps
					if (dcol == 1 && drow == 0)     result = QuoridorController.moveCurrentPawnRight();
					if (dcol == -1 && drow == 0)    result = QuoridorController.moveCurrentPawnLeft();
					if (dcol == 0 && drow == 1)     result = QuoridorController.moveCurrentPawnUp();
					if (dcol == 0 && drow == -1)    result = QuoridorController.moveCurrentPawnDown();

					// Far jumps
					if (dcol == 2 && drow == 0)     result = QuoridorController.jumpCurrentPawnRight();
					if (dcol == -2 && drow == 0)    result = QuoridorController.jumpCurrentPawnLeft();
					if (dcol == 0 && drow == 2)     result = QuoridorController.jumpCurrentPawnUp();
					if (dcol == 0 && drow == -2)    result = QuoridorController.jumpCurrentPawnDown();

					// Lateral jumps
					if (dcol == 1 && drow == 1)     result = QuoridorController.jumpCurrentPawnUpRight();
					if (dcol == -1 && drow == 1)    result = QuoridorController.jumpCurrentPawnUpLeft();
					if (dcol == 1 && drow == -1)    result = QuoridorController.jumpCurrentPawnDownRight();
					if (dcol == -1 && drow == -1)   result = QuoridorController.jumpCurrentPawnDownLeft();

					if (!result) {
						throw new InvalidLoadException("Invalid pawn move for white player: " + action);
					}
				} else {
					// This must be a wall move (by regex)
					final Direction dir;
					switch (wpMove.charAt(2)) {
						case 'v': dir = Direction.Vertical; break;
						case 'h': dir = Direction.Horizontal; break;
						default:  throw new InvalidLoadException("Invalid direction format for white wall: " + wpMove.charAt(2));
					}

					// Grab, move and/or rotate, then plant wall
					grabWall();
					moveWall(wpRow, wpCol);
					final TOWallCandidate candidate = QuoridorController.getWallCandidate();
					if (candidate.getOrientation() != fromDirection(dir)) {
						QuoridorController.rotateWall(candidate);
					}
					if (!QuoridorController.dropWall(candidate.getAssociatedWall())) {
						throw new InvalidLoadException("Invalid wall move for white player: " + action);
					}
				}
			}

			// Only continue if game is running
			// (if a winner is already determined, we ignore)
			if (game.getGameStatus() != GameStatus.Running) {
				break;
			}

			{
				// Play black player's move next
				final String bpMove = matcher.group(3);
				final int bpRow = bpMove.charAt(1) - '1' + 1;
				final int bpCol = bpMove.charAt(0) - 'a' + 1;
				if (bpMove.length() == 2) {
					// This is either a step or a jump move
					final PlayerPosition pos = game.getCurrentPosition().getBlackPosition();
					final Tile t = pos.getTile();

					final int drow = bpRow - t.getRow();
					final int dcol = bpCol - t.getColumn();

					boolean result = false;

					// Shamelessly taken from BoardWindow#onTileClicked(int, int)

					// Steps
					if (dcol == 1 && drow == 0)     result = QuoridorController.moveCurrentPawnRight();
					if (dcol == -1 && drow == 0)    result = QuoridorController.moveCurrentPawnLeft();
					if (dcol == 0 && drow == 1)     result = QuoridorController.moveCurrentPawnUp();
					if (dcol == 0 && drow == -1)    result = QuoridorController.moveCurrentPawnDown();

					// Far jumps
					if (dcol == 2 && drow == 0)     result = QuoridorController.jumpCurrentPawnRight();
					if (dcol == -2 && drow == 0)    result = QuoridorController.jumpCurrentPawnLeft();
					if (dcol == 0 && drow == 2)     result = QuoridorController.jumpCurrentPawnUp();
					if (dcol == 0 && drow == -2)    result = QuoridorController.jumpCurrentPawnDown();

					// Lateral jumps
					if (dcol == 1 && drow == 1)     result = QuoridorController.jumpCurrentPawnUpRight();
					if (dcol == -1 && drow == 1)    result = QuoridorController.jumpCurrentPawnUpLeft();
					if (dcol == 1 && drow == -1)    result = QuoridorController.jumpCurrentPawnDownRight();
					if (dcol == -1 && drow == -1)   result = QuoridorController.jumpCurrentPawnDownLeft();

					if (!result) {
						throw new InvalidLoadException("Invalid pawn move for black player: " + action);
					}
				} else {
					// This must be a wall move (by regex)
					final Direction dir;
					switch (bpMove.charAt(2)) {
						case 'v': dir = Direction.Vertical; break;
						case 'h': dir = Direction.Horizontal; break;
						default:  throw new InvalidLoadException("Invalid direction format for black wall: " + bpMove.charAt(2));
					}

					// Grab, move and/or rotate, then plant wall
					grabWall();
					moveWall(bpRow, bpCol);
					final TOWallCandidate candidate = QuoridorController.getWallCandidate();
					if (candidate.getOrientation() != fromDirection(dir)) {
						QuoridorController.rotateWall(candidate);
					}
					if (!QuoridorController.dropWall(candidate.getAssociatedWall())) {
						throw new InvalidLoadException("Invalid wall move for black player: " + action);
					}
				}
			}
		}

		// Put our game into replay mode if necessary!
		if (game.getGameStatus() == GameStatus.Running) {
			QuoridorController.stopClockForCurrentPlayer();
			game.setGameStatus(GameStatus.ReadyToStart);
		} else {
			game.setGameStatus(GameStatus.Replay);
		}
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

		if (quoridor.numberOfUsers() < 2) {
			// Provide dummy users
			quoridor.addUser("User 1");
			quoridor.addUser("User 2");
		}

		QuoridorController.createGame();

		final Player whitePlayer = new Player(new Time(0, 3, 0), quoridor.getUser(0), 9, Direction.Horizontal);
		final Player blackPlayer = new Player(new Time(0, 3, 0), quoridor.getUser(1), 1, Direction.Horizontal);
		whitePlayer.setNextPlayer(blackPlayer);
		blackPlayer.setNextPlayer(whitePlayer);

		final Game game = quoridor.getCurrentGame();
		game.setWhitePlayer(whitePlayer);
		game.setBlackPlayer(blackPlayer);

		QuoridorController.startNewGame();
		QuoridorController.initiateBoard();

		final GamePosition initialPosition = game.getCurrentPosition();

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
					initialPosition.setPlayerToMove(whitePlayer);
					break;
				case 'B':
					// Sanity check line2 must start with 'W'
					if (line2.charAt(0) != 'W') {
						throw new InvalidLoadException("Bad player color specification: B -> " + line2.charAt(0));
					}
					whiteStarts = false;
					initialPosition.setPlayerToMove(blackPlayer);
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
	 * Plays a step move onto a game position regardless if the move is legal or not
	 *
	 * @param moveNumber    Move number of the move
	 * @param roundNumber   Round number of the move
	 * @param currentPlayer Player of the move
	 * @param target        Tile of the move
	 * @param gamePos       holds pre-state of move, will hold post-state of move
	 * @return A StepMove instance of the move
	 *
	 * @author Paul Teng (260862906)
	 */
	/* package */ static StepMove forcePlayStepMove(int moveNumber, int roundNumber, Player currentPlayer, Tile target,
			GamePosition gamePos) {

		final Game game = gamePos.getGame();

		// Clone the current game position
		final GamePosition newState = deriveNextPosition(gamePos);
		game.setCurrentPosition(newState);

		// Switch the game position
		gamePos = newState;

		if (currentPlayer.hasGameAsWhite()) {
			gamePos.setWhitePosition(new PlayerPosition(currentPlayer, target));
		} else {
			gamePos.setBlackPosition(new PlayerPosition(currentPlayer, target));
		}

		final StepMove stepMove =new StepMove(moveNumber, roundNumber, currentPlayer, target, game);
		game.setCurrentMove(stepMove);

		return stepMove;
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
	/* package */ static boolean anyWallAboveTile(GamePosition gamePos, final int row, final int col) {
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
	 * Checks if any wall is above a particular tile in the current board configuration
	 *
	 * @param row     Row in pawn coordinates
	 * @param col     Column in pawn coordinates
	 * @return true if wall is immediately above, false if not
	 *
	 * @author Group-9
	 */
	public static boolean anyWallAboveTile(final int row, final int col) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for walls when not in game");
		}

		final GamePosition gpos = quoridor.getCurrentGame().getCurrentPosition();
		return anyWallAboveTile(gpos, row, col);
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
	/* package */ static boolean anyWallBelowTile(GamePosition gamePos, final int row, final int col) {
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
	 * Checks if any wall is below a particular tile in the current board configuration
	 *
	 * @param row     Row in pawn coordinates
	 * @param col     Column in pawn coordinates
	 * @return true if wall is immediately below, false if not
	 *
	 * @author Group-9
	 */
	public static boolean anyWallBelowTile(final int row, final int col) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for walls when not in game");
		}

		final GamePosition gpos = quoridor.getCurrentGame().getCurrentPosition();
		return anyWallBelowTile(gpos, row, col);
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
	/* package */ static boolean anyWallRightOfTile(GamePosition gamePos, final int row, final int col) {
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
	 * Checks if any wall is on the right of a particular tile in the current board configuration
	 *
	 * @param row     Row in pawn coordinates
	 * @param col     Column in pawn coordinates
	 * @return true if wall is on the immediate right, false if not
	 *
	 * @author Group-9
	 */
	public static boolean anyWallRightOfTile(final int row, final int col) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for walls when not in game");
		}

		final GamePosition gpos = quoridor.getCurrentGame().getCurrentPosition();
		return anyWallRightOfTile(gpos, row, col);
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
	/* package */ static boolean anyWallLeftOfTile(GamePosition gamePos, final int row, final int col) {
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
	 * Checks if any wall is on the left of a particular tile in the current board configuration
	 *
	 * @param row     Row in pawn coordinates
	 * @param col     Column in pawn coordinates
	 * @return true if wall is on the immediate left, false if not
	 *
	 * @author Group-9
	 */
	public static boolean anyWallLeftOfTile(final int row, final int col) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for walls when not in game");
		}

		final GamePosition gpos = quoridor.getCurrentGame().getCurrentPosition();
		return anyWallLeftOfTile(gpos, row, col);
	}

	/**
	 * Plays a jump move onto a game position regardless if the move is legal or not
	 *
	 * @param moveNumber    Move number of the move
	 * @param roundNumber   Round number of the move
	 * @param currentPlayer Player of the move
	 * @param target        Tile of the move
	 * @param gamePos       holds pre-state of move, will hold post-state of move
	 * @return A JumpMove instance of the move
	 *
	 * @author Paul Teng (260862906)
	 */
	/* package */ static JumpMove forcePlayJumpMove(int moveNumber, int roundNumber, Player currentPlayer, Tile target,
			GamePosition gamePos) {

		final Game game = gamePos.getGame();

		// Clone the current game position
		final GamePosition newState = deriveNextPosition(gamePos);
		game.setCurrentPosition(newState);

		// Switch the game position
		gamePos = newState;

		if (currentPlayer.hasGameAsWhite()) {
			gamePos.setWhitePosition(new PlayerPosition(currentPlayer, target));
		} else {
			gamePos.setBlackPosition(new PlayerPosition(currentPlayer, target));
		}

		final JumpMove jumpMove=new JumpMove(moveNumber, roundNumber, currentPlayer, target, game);
		game.setCurrentMove(jumpMove);
		return jumpMove;

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
	public static TOPlayer fromPlayer(Player p) {
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
	private static void runClockForPlayer(final Player player) {
		if (player == null) {
			// wut?
			return;
		}

		// Create a task that, on each tick,
		// decreases remaining time of player
		final TimerTask task = new TimerTask() {
			@Override
			public void run() {
				final Game g;
				final boolean isWhitePlayer;
				if (player.hasGameAsWhite()) {
					g = player.getGameAsWhite();
					isWhitePlayer = true;
				} else {
					g = player.getGameAsBlack();
					isWhitePlayer = false;
				}

				if (g != null && g.getGameStatus() != GameStatus.Running) {
					// Make sure time only decreases when game is being played
					return;
				}

				final Time remTime = player.getRemainingTime();
				if (remTime.getHours() > 0 || remTime.getMinutes() > 0 || remTime.getSeconds() > 0) {
					// Subtract time by milliseconds per tick:
					// getTime() works with milliseconds
					final Time newTime = new Time(Math.max(0, remTime.getTime() - TIME_PER_TICK_MS));

					// Update the remaining time
					player.setRemainingTime(newTime);
				} else {
					// This means the player ran out of time!

					// the other player wins!
					if (!isWhitePlayer) {
						QuoridorController.setWinner(g.getWhitePlayer());
					} else {
						QuoridorController.setWinner(g.getBlackPlayer());
					}

					// stop the task
					QuoridorController.stopClockForPlayer(player);
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
	 * Starts the clock for the current player
	 *
	 * @author Group 9
	 */
	public static void runClockForCurrentPlayer() {
		runClockForPlayer(QuoridorApplication.getQuoridor()
				.getCurrentGame()
				.getCurrentPosition()
				.getPlayerToMove());
	}

	/**
	 * Stops the clock for the current player
	 *
	 * @author Group 9
	 */
	public static void stopClockForCurrentPlayer() {
		stopClockForPlayer(QuoridorApplication.getQuoridor()
				.getCurrentGame()
				.getCurrentPosition()
				.getPlayerToMove());
	}

	/**
	 * Sets the winner of the game
	 *
	 * @param p the winner, does nothing if null
	 *
	 * @author Group-9
	 */
	private static void setWinner(Player p) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game game = quoridor.getCurrentGame();

		if (p == null) {
			return;
		}

		if (p.hasGameAsWhite()) {
			game.setGameStatus(GameStatus.WhiteWon);
		} else {
			game.setGameStatus(GameStatus.BlackWon);
		}

		// Do more proper cleanup here
		game.setWallMoveCandidate(null);
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

	/**
	 * Creates a pawn state machine for the current player and game and initializes
	 * it
	 *
	 * @return An initialized pawn state machine
	 *
	 * @author Group-9
	 */
	private static PawnBehavior setupPawnStateMachine() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to use state machine when no game exists");
		}

		final Game game = quoridor.getCurrentGame();
		final PawnBehavior sm = new PawnBehavior();
		sm.setCurrentGame(game);
		sm.setPlayer(game.getCurrentPosition().getPlayerToMove());
		sm.initialize();

		return sm;
	}

	/**
	 * Create a pawn state machine for the specified player (current game) and
	 * initializes it. A method solely used by tester.
	 *
	 * @return An initialized pawn state machine
	 *
	 * @author Paul Teng (260862906)
	 */
	public static PawnBehavior setupPawnStateMachineForPlayer(Color color) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to use state machine when no game exists");
		}

		final Game game = quoridor.getCurrentGame();
		final PawnBehavior sm = new PawnBehavior();
		sm.setCurrentGame(game);
		sm.setPlayer(getModelPlayerByColor(color));
		sm.initialize();

		return sm;
	}

	/**
	 * Tries to move the current pawn upwards by 1 row
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean moveCurrentPawnUp() {

		return setupPawnStateMachine().moveUp();

	}

	/**
	 * Tries to move the current pawn downards by 1 row
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean moveCurrentPawnDown() {
		return setupPawnStateMachine().moveDown();
	}

	/**
	 * Tries to move the current pawn leftwards by 1 column
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean moveCurrentPawnLeft() {
		return setupPawnStateMachine().moveLeft();
	}

	/**
	 * Tries to move the current pawn rightwards by 1 column
	 *
	 * @return true if move succeeds, false if faled
	 *
	 * @author Group-9
	 */
	public static boolean moveCurrentPawnRight() {
		return setupPawnStateMachine().moveRight();
	}

	/**
	 * Tries to move the current pawn upwards by 2 rows
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnUp() {
		return setupPawnStateMachine().jumpUp();
	}

	/**
	 * Tries to move the current pawn dowards by 2 rows
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnDown() {
		return setupPawnStateMachine().jumpDown();
	}

	/**
	 * Tries to move the current pawn leftwards by 2 columns
	 *
	 * @return true if move succeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnLeft() {
		return setupPawnStateMachine().jumpLeft();
	}

	/**
	 * Tries to move the current pawn rightwards by 2 columnss
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnRight() {
		return setupPawnStateMachine().jumpRight();
	}

	/**
	 * Tries to move the currrent pawn in the upwards-right direction
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnUpRight() {
		final PawnBehavior sm = setupPawnStateMachine();
		return sm.jumpUpRight() || sm.jumpRightUp();
	}

	/**
	 * Tries to move the currrent pawn in the downwards-right direction
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnDownRight() {
		final PawnBehavior sm = setupPawnStateMachine();
		return sm.jumpDownRight() || sm.jumpRightDown();
	}

	/**
	 * Tries to move the currrent pawn in the upwards-left direction
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnUpLeft() {
		final PawnBehavior sm = setupPawnStateMachine();
		return sm.jumpUpLeft() || sm.jumpLeftUp();
	}

	/**
	 * Tries to move the currrent pawn in the downwards-left direction
	 *
	 * @return true if move succeeds, false if failed
	 *
	 * @author Group-9
	 */
	public static boolean jumpCurrentPawnDownLeft() {
		final PawnBehavior sm = setupPawnStateMachine();
		return sm.jumpDownLeft() || sm.jumpLeftDown();
	}

	/**
	 * Creates the path nodes.
	 *
	 * Note: Wall candidate information is not taken into account
	 *
	 * @param gpos GamePosition
	 * @return the path nodes as a 2D array
	 *
	 * @author Paul Teng (260862906)
	 */
	private static Node[][] createPathNodes(GamePosition gpos) {
		// Board is 9 by 9
		final Node[][] nodes = new Node[9][9];

		// Link every node together (assume there are no walls)
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				final Node curr = new Node();
				nodes[i][j] = curr;

				if (i > 0)
					curr.linkSouth(nodes[i - 1][j]);
				if (j > 0)
					curr.linkWest(nodes[i][j - 1]);
			}
		}

		// Remove the links blocked by walls
		Stream.concat(gpos.getWhiteWallsOnBoard().stream(), gpos.getBlackWallsOnBoard().stream()).map(Wall::getMove)
				.forEach(move -> {
					final Tile t = move.getTargetTile();
					final Direction dir = move.getWallDirection();
					QuoridorController.unlinkNodeWithWallMove(nodes, t.getRow(), t.getColumn(), fromDirection(dir));
				});
		return nodes;
	}

	/**
	 * Unlinks node with a wall
	 *
	 * @param nodeMap     Node map (9 by 9 grid)
	 * @param row         Row in wall coordinates
	 * @param col         Column in wall coordinates
	 * @param orientation Orientation
	 *
	 * @author Paul Teng (260862906)
	 */
	private static void unlinkNodeWithWallMove(Node[][] nodeMap, int row, int col, Orientation orientation) {
		switch (orientation) {
		case VERTICAL:
			nodeMap[row - 1][col - 1].unlinkEast();
			nodeMap[row][col - 1].unlinkEast();
			break;
		case HORIZONTAL:
			nodeMap[row - 1][col - 1].unlinkNorth();
			nodeMap[row - 1][col].unlinkNorth();
			break;
		}
	}

	/**
	 * Creates path finder for player based on the node map
	 *
	 * @param gpos    GamePosition
	 * @param color   Color of the player
	 * @param nodeMap Node map
	 * @return the path finder for that player
	 *
	 * @see QuoridorController#createPathNodes(GamePosition) this creates the node
	 *      map
	 *
	 * @author Paul Teng (260862906)
	 */
	private static PathFinder createPathFinderForPlayer(GamePosition gpos, Color color, Node[][] nodeMap) {
		final Game game = gpos.getGame();
		final PathFinder finder = new PathFinder();

		// Set the starting node
		final Tile t;
		final Destination d;
		switch (color) {
		case WHITE:
			t = gpos.getWhitePosition().getTile();
			d = game.getWhitePlayer().getDestination();
			break;
		case BLACK:
			t = gpos.getBlackPosition().getTile();
			d = game.getBlackPlayer().getDestination();
			break;
		default:
			throw new UnsupportedOperationException("Unsupported path test for player with color: " + color);
		}
		finder.setStartingNode(nodeMap[t.getRow() - 1][t.getColumn() - 1]);

		// Set the ending nodes
		final HashSet<Node> set = new HashSet<>();
		final int k = d.getTargetNumber() - 1;
		switch (d.getDirection()) {
		case Vertical:
			for (int i = 0; i < 9; ++i) {
				set.add(nodeMap[i][k]);
			}
			break;
		case Horizontal:
			for (int i = 0; i < 9; ++i) {
				set.add(nodeMap[k][i]);
			}
			break;
		}
		finder.setEndingNodes(set);

		return finder;
	}

	/**
	 * Returns the index of a specific move inside the list of moves of the game, by its move number and round number
	 *
	 * @param movno
	 * @param rndno
	 * @return
	 *
	 * @author alixe delabrousse
	 *
	 */

	public static int getIndexFromMoveAndRoundNumber(int movno, int rndno) {
		if (rndno == 2) {
			return movno*2-1;
		} else if (rndno == 1) {
			return (movno-1)*2;
		} else {
			return -1;
		}
	}

	/**
	 * @author alixe delabrousse
	 * 
	 * When the game is in replay mode, 
	 * this method sets the current position to the final position of the game.
	 * If the game is already at the last position, it stays in the same position.
	 *
	 * @return boolean isAtFinalPos - checks if the game is already at the final position
	 */
	
	public static boolean jumpToFinalPosition() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		boolean isAtFinalPos = false;
		Move aMove = game.getCurrentMove();
		
		if (game.getGameStatus() == GameStatus.Replay) {
			Move lastMove = game.getMove(game.numberOfMoves()-1);
			if (aMove != lastMove) isAtFinalPos = true;
			game.setCurrentMove(lastMove);
			GamePosition gamePos = game.getPositions().get(game.numberOfPositions()-1);
			game.setCurrentPosition(gamePos);
			
		} else {
			System.out.println("The game is not in replay mode");
		}
		 return isAtFinalPos;

	}
	/**
	 * @author alixe delabrousse
	 * 
	 * When the game is in replay mode,
	 * this method sets the current position to the start position.
	 * The white player starts in at position (1, 5) whereas the black player starts at (9, 5)
	 * ((row, column)).
	 * If the position of the game is already at the start position, it stays at this position.
	 *
	 * @return boolean isAtFirstPos - to check if the game is already at the start position
	 */

	
	public static boolean jumpToStartPosition() {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		boolean isAtFirstPos = false;
		Move aMove = game.getCurrentMove();
		
		if (game.getGameStatus() == GameStatus.Replay){		
			Move firstMove = game.getMove(0);
			if (aMove != firstMove) isAtFirstPos = true;
			GamePosition gamePos = game.getPosition(0);
			game.setCurrentPosition(gamePos);
			game.setCurrentMove(firstMove);
			
		} else {
			System.out.println("The game is not in replay mode");
		}
	
		return isAtFirstPos;
	}

	/**
	 * @author alixe delabrousse
	 * 
	 * This method returns the corresponding column number from its letter.
	 * 
	 * @param letter
	 * @return
	 */

	public static int letterToNumberColumn(char letter) {
		switch (letter) {
			case 'a':
				return 1;
			case 'b':
				return 2;
			case 'c':
				return 3;
			case 'd':
				return 4;
			case 'e':
				return 5;
			case 'f':
				return 6;
			case 'g':
				return 7;
			case 'h':
				return 8;
			case 'i':
				return 9;
			default:
				return -1;

		}

	}
	
	public static GamePosition saveGamePos;
	public static int index = 0;
			
	public static void enterReplayMode(){

		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game aGame = quoridor.getCurrentGame();
		switch(aGame.getGameStatus()) {
		case Running:
		case WhiteWon:
		case BlackWon:
		case Draw:
			System.out.println("ENTER REPLAY MODE");
			aGame.setGameStatus(GameStatus.Replay);
		}
		//aGame.getCurrentMove()
		saveGamePos = aGame.getCurrentPosition();
		index = aGame.indexOfMove(aGame.getCurrentMove());

	}

	public static void exitReplayMode(){

		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game aGame = quoridor.getCurrentGame();
		if(aGame.getGameStatus() == GameStatus.Replay) {
			System.out.println("EXIT REPLAY MODE");
			aGame.setCurrentPosition(saveGamePos);
			aGame.setGameStatus(GameStatus.Running);
			
			// In case someone tries to exit replay mode after game ended
			initiateCheckGameResult();
			
		}
	}


	/*
	Given The game is running
	Scenario Outline: Player resigns
    Given The player to move is "<player>"
    When Player initates to resign
    Then Game result shall be "<result>"
    And The game shall no longer be running
	 */

	public static void playerResigns() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Game aGame = quoridor.getCurrentGame();

		if(aGame.getGameStatus() == GameStatus.Running){
			TOPlayer currentPlayer= getPlayerOfCurrentTurn();
			if(currentPlayer.getColor()==Color.WHITE) {
				//System.out.println("Black Wins");
				aGame.setGameStatus(GameStatus.BlackWon);
			}
			else {
				//System.out.println("White Wins");
				aGame.setGameStatus(GameStatus.WhiteWon);
			}
		}
	}

	/**
	 * Checks to see if any player has won and returns the color.
	 *
	 * Note: This is based on the status of the game!
	 *
	 * @return color of the winner, none if no winner, all if draw
	 *
	 * @author Paul Teng (260862906)
	 */
	public static EnumSet<Color> getWinner() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// No Game No Winner
			return EnumSet.noneOf(Color.class);
		}

		return getWinnerForGame(quoridor.getCurrentGame());
	}

	/**
	 * Checks to see if any player has won for a particular game and returns the color.
	 *
	 * Note: This is based on the status of the game!
	 *
	 * @return color of the winner, none if no winner, all if draw
	 *
	 * @author Paul Teng (260862906)
	 */
	private static EnumSet<Color> getWinnerForGame(final Game game) {
		final GameStatus status = game.getGameStatus();
		switch (status) {
			case WhiteWon:
				return EnumSet.of(Color.WHITE);
			case BlackWon:
				return EnumSet.of(Color.BLACK);
			case Draw:
				return EnumSet.allOf(Color.class);
			default:
				return EnumSet.noneOf(Color.class);
		}
	}

	/**
	 * Checks to see if game should terminate (due to winning or draw conditions)
	 *
	 * @return true if game status is changed, false otherwise
	 *
	 * @author Group-9
	 */
	public static boolean initiateCheckGameResult() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// Nothing to process
			return false;
		}

		Game game = quoridor.getCurrentGame();
		if (game.getGameStatus() != GameStatus.Running) { //if game is not running
			// Nothing to process
			return false;
		}
		
		int whiteCounter =0, blackCounter = 0; 

		List<String> moves = getMovesAsStrings(); 

		int lastIndexWhite = moves.size()-1; 
		int lastIndexBlack = moves.size()-2;
		if (moves.size() >= 2) {//check if list is empty

			//check for duplicate position
			for (int i=moves.size()-5; i>=0;i= i-4) {
		 		if (moves.get(lastIndexWhite).equals(moves.get(i))) {
					whiteCounter++; 
					if (whiteCounter ==2) {
						break; 
					}
		 		} else break;
			}

			//check for duplicate positions
			for (int j=moves.size() -6; j>=0;j=j-4) {
		 		if (moves.get(lastIndexBlack).equals(moves.get(j))) {
					blackCounter++; 
					if (whiteCounter ==2) {
						break; 
					}
				}else break; 
			}	

			if (whiteCounter==2 || blackCounter == 2) {
				game.setGameStatus(GameStatus.Draw); 
				QuoridorController.stopClockForCurrentPlayer();
				game.setWallMoveCandidate(null);
				return true; 
			}
		}
	

		// then call setWinner(p) on the correct player

		GamePosition gpos = game.getCurrentPosition();
		if (testDestinationAndTile(game.getWhitePlayer().getDestination(), gpos.getWhitePosition().getTile())) {
			setWinner(game.getWhitePlayer());
			return true;
		}
		if (testDestinationAndTile(game.getBlackPlayer().getDestination(), gpos.getBlackPosition().getTile())) {
			setWinner(game.getBlackPlayer());
			return true;
		}

		return false;
	}

	/**
	 * This is the save game method. 
	 * @param String filePath, boolean overwriteIfExists
	 * @return boolean
	 */

	public static boolean saveGame(String filePath, boolean overwriteIfExists) throws IOException {
		final File file = new File(filePath);
		if (file.exists() && !overwriteIfExists) {
			// File exists but user does not want to
			// overwrite the file, so we are done
			return false;
		}
		
		try (final Writer writer = new FileWriter(file)) {
			final Quoridor quoridor = QuoridorApplication.getQuoridor();
			if (!quoridor.hasCurrentGame()) {
				return true;
			}

			// StringBuilder sb = new StringBuilder();
			final List<Move> listOfMoves = quoridor.getCurrentGame().getMoves();
			for (int m =1, i = 0; i < listOfMoves.size(); m++, i += 2) {
				final Move move = listOfMoves.get(i);
				final Move move2 = listOfMoves.get(i + 1);

				writer.append(Integer.toString(m)).append(". ");

				Tile t = move.getTargetTile();
				writer.append((char) (t.getColumn() + 'a' - 1));
				writer.append((char) (t.getRow() + '1' - 1));

				if (move instanceof WallMove) {
					writer.append(Character.toLowerCase(((WallMove) move).getWallDirection().name().charAt(0)));
				}

				writer.append(' ');

				t = move2.getTargetTile();
				writer.append((char) (t.getColumn() + 'a' - 1));
				writer.append((char) (t.getRow() + '1' - 1));

				if (move2 instanceof WallMove) {
					writer.append(Character.toLowerCase(((WallMove) move2).getWallDirection().name().charAt(0)));
				}

				writer.append(System.lineSeparator());
			}
		}
		return true;
	}
	/**
	 * Checks to see if the tile matches the provided destination
	 *
	 * @param dest destination of the player
	 * @param tile tile of the player
	 * @return true if destination reached, false otherwise
	 *
	 * @author Group-9
	 */
	private static boolean testDestinationAndTile(Destination dest, Tile tile) {
		switch (dest.getDirection()) {
		case Vertical:
			return tile.getColumn() == dest.getTargetNumber();
		case Horizontal:
			return tile.getRow() == dest.getTargetNumber();
		default:
			throw new AssertionError("Unhandled target direction: " + dest.getDirection());
		}
	}

	/**
	 * Returns the list of moves as string. Think it would look cool for the game...
	 *
	 * @return the list of moves as string.
	 *
	 * @author Paul Teng (260862906)
	 */
	public static List<String> getMovesAsStrings() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			return Collections.emptyList();
		}

		// Store moves as a list of string
		final List<String> resultList = new LinkedList<>();

		final List<Move> listOfMoves = quoridor.getCurrentGame().getMoves();
		for (int i = 0; i < listOfMoves.size(); ++i) {
			final Move move = listOfMoves.get(i);

			// Build-up the move as string
			final StringBuilder movestr = new StringBuilder();
			if (move.getPlayer().hasGameAsWhite()) {
				movestr.append("White player: ");
			} else {
				movestr.append("Black player: ");
			}

			final Tile t = move.getTargetTile();
			movestr.append((char) (t.getColumn() + 'a' - 1));
			movestr.append((char) (t.getRow() + '1' - 1));

			if (move instanceof WallMove) {
				movestr.append(Character.toLowerCase(((WallMove) move).getWallDirection().name().charAt(0)));
			}

			resultList.add(movestr.toString());
		}
		return resultList;
	}

	/**
	 * Returns index of the current game position
	 *
	 * @return index of the current game position, -1 if no game position exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static int getIndexOfCurrentFrame() {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			return -1;
		}

		final Game game = quoridor.getCurrentGame();
		return game.indexOfPosition(game.getCurrentPosition()) - 1;
	}

}// end QuoridorController
