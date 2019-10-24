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
import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
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

	/////////////////////////// FIELDS ///////////////////////////
	
	private static Quoridor quoridor;
	private static Board board;
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
	 *
	 * This method sets up a new game
	 * 
	 * 
	 */
	
	public Game startNewGame(){
		throw new UnsupportedOperationException("method startNewGame is not implemented yet");
	}
	
	
	/**
	 * 
	 * @author Barry Chen 
	 * 
	 * @param none
	 * @returns initialized board
	 * 
	 * This method initialize the board which place both players' pawn at its initial position
	 * 
	 * 
	 */
	
	public Board initiateBoard() {
		throw new UnsupportedOperationException("method initiateBoard is not implemented yet");
	}
	

	
	
	/**
	 * This method checks if the given username already exists.
	 * 
	 * @param String user;  
	 * @return boolean user; 
	 * 
	 * @author Ada Andrei
	 */

	public static boolean usernameExists(boolean user) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		
	}

	/**
	 * This method allows the user to create a new username.
	 * 
	 * @param String user;  
	 * @return void; 
	 * 
	 * @author Ada Andrei
	 */

	public static void selectUsername(String user) throws UnsupportedOperationException{
		if (true) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * This method allows the user to select it an existing username.
	 * 
	 * @param int userIndex;  
	 * @return void; 
	 * 
	 * @author Ada Andrei
	 */

	public static void createUsername(String user) throws UnsupportedOperationException{
		if (true) {
			throw new UnsupportedOperationException();
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
	
	public static void setTime(int mins, int secs) throws UnsupportedOperationException {
		if (true) {
			throw new UnsupportedOperationException();
		}
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
	
	public static void grabWall(List<TOWall> wallStock) {
		throw new UnsupportedOperationException();
		
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
	
	public static void moveWall(String side) {
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * 
	 * @author mohamed Mohamed
	 * 
	 * @param wallCandidate
	 * 
	 * This method allows you to rotate a wall that is already held and change it's orientation to horizontal or to vertical
	 * 
	 * 
	 */
	
	public static void rotateWall(TOWallCandidate wallCandidate) {
		//this method should change the direction of the candidate
		wallCandidate.rotate(); // change the orientation of the wall
		
		
	}
	
	/**
	 * 
	 * @author Mohamed Mohamed
	 * 
	 * @param wall
	 * @param destination
	 * 
	 * This methods allows you to drop the wall that it is in the users hand.
	 * 
	 * 
	 */
	
	public static void dropWall(TOWall wall) {
		//this method will drop 
		int row= wall.getRow();
		int column= wall.getColumn();
		Orientation orientation= wall.getOrientation();
		
		
		boolean isValid = validWallPlacement(row, column, orientation);
		if (isValid==true) {
			//if true create a wallmove
			Move myMove= createWallMove(row, column, orientation);
		}else {
			throw new UnsupportedOperationException();	
		}
		
		
		
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

		// Clone the current game position but ith playerToMove swapped
		final Game game = quoridor.getCurrentGame();
		
		final GamePosition newState = deriveNextPosition(game.getCurrentPosition());
		newState.setPlayerToMove(newState.getPlayerToMove().getNextPlayer());
		game.setCurrentPosition(newState);
	}

	/**
	 * Derives anoother game position with the next id, same player positions.
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
	 * @returns true if position is valid, false otherwise
	 *
	 * @author Group 9
	 */
	public static boolean validatePawnPlacement(final int row, final int column) {
		// Position must be on the board for it to be potentially valid
		if (!isValidPawnCoordinate(row, column)) {
			return false;
		}

		// Check all (2 of them) pawns on the board.
		// If no overlapping, it must be good to place it down
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for pawn placement when not in game");
		}
		
		final Game game = quoridor.getCurrentGame();
		final GamePosition pos = game.getCurrentPosition();

		final Tile whiteTile = pos.getWhitePosition().getTile();
		final Tile blackTile = pos.getBlackPosition().getTile();

		return !(whiteTile.getRow() == row && whiteTile.getColumn() == column)
			&& !(blackTile.getRow() == row && blackTile.getColumn() == column);
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
	 * creates a wall move that will be than given to validate a wall placement
	 * should call the validwallposition, if true, allowing the
	 * creation of the wall move.
	 *
	 * @param row The row of the wall.
	 * @param column The column of the wall
	 * @param orientation Orientation of the wall
	 * @returns true if the wall move is created, false otherwise
	 *
	 * @author Mohamed Mohamed
	 */
	public static WallMove createWallMove(int row, int column, Orientation orientation) {
	
	//        game has his list of moves
	//        player has his list of walls	
		
	Tile tile = new Tile(row, column, board); 
	int moveNumber= 0; // ????
	int roundNumber= 0; // ????
	int numOfWall= 0; // ????
	Direction direction=null;
	
	
	if(orientation==orientation.HORIZONTAL) {
		direction=direction.Horizontal;
	}else {
		direction=direction.Vertical;
	}
	int index = 0; // ????
	Wall thisWall= currentPlayer.getWall(index);
	WallMove wallMove= new WallMove(moveNumber, roundNumber, currentPlayer, tile, game, direction, thisWall);
	return wallMove;
	}
	
	/**
	 * indicates if a wall placement is valid (i.e if the wall could possible put at this position
	 * should tell the user if the wall move is not valid
	 *
	 * @param row The row of the wall.
	 * @param column The column of the wall
	 * @param orientation Orientation of the wall
	 * @returns true if the position is valid, false otherwise
	 *
	 * @author Mohamed Mohamed
	 */
	public static boolean validWallPlacement(int row, int column, Orientation orientation) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Validates a placement of a wall
	 *
	 * @param row Row of wall
	 * @param column Column of wall
	 * @param orientation Orientation of wall
	 * @returns true if position is valid, false otherwise
	 *
	 * @author Group 9
	 */
	public static boolean validateWallPlacement(final int row, final int column, Orientation orientation) {
		// If both of the tiles are out of the board, the placement must be invalid
		if (!isValidWallCoordinate(row, column)) {
			return false;
		}

		// Calculate the location of the 2nd tile occupied by wall
		final int t2Row;
		final int t2Col;
		switch (orientation) {
			case HORIZONTAL:
				t2Row = row + 1;
				t2Col = column;
				break;
			case VERTICAL:
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

		// Check all walls on the board
		// If no overlapping, it must be good to place it down
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			throw new IllegalStateException("Attempt to check for wall placement when not in game");
		}
		
		final Game game = quoridor.getCurrentGame();
		final GamePosition pos = game.getCurrentPosition();

		for (Wall w : pos.getWhiteWallsOnBoard()) {
			// Since on board, must have WallMove associated with it
			if (wallMoveOverlapsWithPlacement(w.getMove(), row, column, t2Row, t2Col)) {
				return false;
			}
		}

		for (Wall w : pos.getBlackWallsOnBoard()) {
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
	 * @returns false if we do not overwrite, true if save operation succeeds
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

		pw.println("# PLEASE DO NOT EDIT THIS FILE");
		pw.println("# The content was auto-generated");

		pw.printf("status:%s\n", game.getGameStatus().name());
		pw.printf("move:%s\n", game.getMoveMode().name());

		pw.println("# White Player Info");
		savePlayer(pw, game.getWhitePlayer());
		pw.println("#");
		
		pw.println("# Black Player Info");
		savePlayer(pw, game.getBlackPlayer());
		pw.println("#");

		pw.println("# List of moves");
		saveMoves(pw, game.getMoves());
		pw.println("#");
	}

	/**
	 * Writes out a list of moves of the game
	 *
	 * @param pw The stream we are writing to
	 * @param moves The moves associated to a game
	 * @throws IOException If writing operation fails
	 *
	 * @author Paul Teng (260862906)
	 */
	private static void saveMoves(PrintWriter pw, List<Move> moves) throws IOException {
		pw.printf("cnt:%d\n", moves.size());
		for (final Move move : moves) {
			pw.printf("moveNumber:%d\n", move.getMoveNumber());
			pw.printf("roundNumber:%d\n", move.getRoundNumber());
			if (move.getPlayer().hasGameAsWhite()) {
				pw.printf("player:%s\n", Color.WHITE.name());
			} else {
				pw.printf("player:%s\n", Color.BLACK.name());
			}
			saveTile(pw, move.getTargetTile());

			// I will take the liberty to assume that move (abstract) must be one of the following:
			// - StepMove
			// - JumpMove
			// - WallMove
			//
			// Out of these three, only WallMove needs more stuff to be written-out
			pw.printf("type:%s\n", move.getClass().getSimpleName());
			if (move instanceof WallMove) {
				final WallMove wallMove = (WallMove) move;
				pw.println("# WallMove specific fields");
				pw.printf("dir:%s\n", wallMove.getWallDirection().name());
				pw.printf("id:%d\n", wallMove.getWallPlaced().getId());
				pw.println("#");
			}
		}
	}

	/**
	 * Writes out a tile position
	 * 
	 * @param pw The stream we are writing to
	 * @param tile The tile being saved
	 * @throws IOException If writing operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static void saveTile(PrintWriter pw, Tile tile) throws IOException {
		pw.printf("row:%d\n", tile.getRow());
		pw.printf("col:%d\n", tile.getColumn());
	}

	/**
	 * Writes out a player
	 * 
	 * @param pw The stream we are writing to
	 * @param p The player being saved
	 * @throws IOException If writing operation fails
	 */
	private static void savePlayer(PrintWriter pw, Player p) throws IOException {
		final Time t = p.getRemainingTime();
		pw.printf("time:%d:%d:%d\n", t.getHours(), t.getMinutes(), t.getSeconds());
		pw.printf("name:%s\n", p.getUser().getName());
		
		final Destination d = p.getDestination();
		pw.printf("target:%d\n", d.getTargetNumber());
		pw.printf("dir:%s\n", d.getDirection().name());

		final List<Wall> walls = p.getWalls();
		pw.printf("walls:%d\n", walls.size());
		for (Wall w : walls) {
			pw.printf("id:%d\n", w.getId());
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

		final GameStatus status = matchForEnum(br, "status", GameStatus.class);
		final MoveMode move = matchForEnum(br, "move", MoveMode.class);

		final Player whitePlayer = readPlayer(br);
		final Player blackPlayer = readPlayer(br);
		whitePlayer.setNextPlayer(blackPlayer);
		blackPlayer.setNextPlayer(whitePlayer);

		final Game game;
		if (!quoridor.hasCurrentGame()) {
			game = new Game(status, move, quoridor);
		} else {
			game = quoridor.getCurrentGame();
			game.setGameStatus(status);
			game.setMoveMode(move);
		}
		game.setWhitePlayer(whitePlayer);
		game.setBlackPlayer(blackPlayer);

		// White player starts the game:
		// XXX: May want make it so the save file defines who starts
		final GamePosition initialPosition = createInitialGamePosition(whitePlayer, blackPlayer, whitePlayer, game);

		// And we set this as the current position of game
		game.setCurrentPosition(initialPosition);

		readMoves(br, game);

		// No need to validate position here since
		// readMoves does (quite a bit) of that

		// Do remember to switch the player though...
		switchCurrentPlayer();
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
	 * Reads in a list of moves to be added to a game
	 * 
	 * @param br The stream we are reading from
	 * @param g The game performing / registering these moves
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static void readMoves(BufferedReader br, Game g) throws IOException, InvalidLoadException {
		// TODO: Slim this method down...

		Move lastMove = null; // since move works like a double-linked list

		final int count = matchForInt(br, "cnt");
		for (int i = 0; i < count; ++i) {
			final int moveNumber = matchForInt(br, "moveNumber");
			final int roundNumber = matchForInt(br, "roundNumber");

			final Player currentPlayer;
			final Color playerColor = matchForEnum(br, "player", Color.class);
			switch (playerColor) {
				case WHITE:
					currentPlayer = g.getWhitePlayer();
					break;
				case BLACK:
					currentPlayer = g.getBlackPlayer();
					break;
				default:
					throw new InvalidLoadException("Unsupported color for player: " + playerColor);
			}

			final Tile targetTile = readTile(br);

			// Try to replay the move that was just read!
			final GamePosition newState = deriveNextPosition(g.getCurrentPosition());
			newState.setPlayerToMove(currentPlayer);

			final Move currentMove;
			final String simpleClassName = matchForString(br, "type");
			switch (simpleClassName) {
				case "StepMove": {
					currentMove = new StepMove(moveNumber, roundNumber, currentPlayer, targetTile, g);
					switch (playerColor) {
						case WHITE:
							newState.setWhitePosition(new PlayerPosition(currentPlayer, targetTile));
					break;
						case BLACK:
							newState.setBlackPosition(new PlayerPosition(currentPlayer, targetTile));
							break;
					}
					break;
				}
				case "JumpMove": {
					currentMove = new JumpMove(moveNumber, roundNumber, currentPlayer, targetTile, g);
					switch (playerColor) {
						case WHITE:
							newState.setWhitePosition(new PlayerPosition(currentPlayer, targetTile));
					break;
						case BLACK:
							newState.setBlackPosition(new PlayerPosition(currentPlayer, targetTile));
							break;
					}
					break;
				}
				case "WallMove": {
					final Direction dir = matchForEnum(br, "dir", Direction.class);
					final Wall wall = Wall.getWithId(matchForInt(br, "id"));

					if (wall.getOwner() != currentPlayer) {
						throw new InvalidLoadException("Player(" + playerColor + ")=" + currentPlayer.getUser().getName() + " tried to grab wall of player " + wall.getOwner().getUser().getName());
					}

					final WallMove wallMove = new WallMove(moveNumber, roundNumber, currentPlayer, targetTile, g, dir, wall);
					currentMove = wallMove;

					wall.setMove(wallMove);
					switch (playerColor) {
						case WHITE:
							newState.removeWhiteWallsInStock(wall);
							newState.addWhiteWallsOnBoard(wall);
							break;
						case BLACK:
							newState.removeBlackWallsInStock(wall);
							newState.addBlackWallsOnBoard(wall);
							break;
					}
					break;
				}
				default:
					throw new InvalidLoadException("Unsupported move type with simple name: " + simpleClassName);
			}

			if (!validateGamePosition(newState)) {
				// If the new game position is invalid, then we crash!
				throw new InvalidLoadException("Illegal board configuration after replaying:\n" +
						"  i=" + i + ",\n" +
						"  moveNumber=" + moveNumber + ",\n" +
						"  roundNumber=" + roundNumber + ",\n" +
						"  type=" + simpleClassName + ",\n" +
						"  by(" + playerColor + ")=" + currentPlayer.getUser().getName());
			}

			g.setCurrentPosition(newState);

			// link the moves together
			currentMove.setPrevMove(lastMove);
			lastMove = currentMove;
		}
	}

	/**
	 * Reads in a tile position
	 * 
	 * @param br The stream we are reading from
	 * @return The tile being read
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Tile readTile(BufferedReader br) throws IOException, InvalidLoadException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();

		final int row = matchForInt(br, "row");
		final int col = matchForInt(br, "col");

		if (!isValidPawnCoordinate(row, col)) {
			throw new InvalidLoadException("Illegal coordinate (" + row + "," + col + ")");
		}

		// based off indexing scheme used in
		// CucumberStepDefintions#theFollowingWallsExist(DataTable)
		return quoridor.getBoard().getTile((row - 1) * 9 + col - 1);
	}

	/**
	 * Reads in a player
	 * 
	 * @param br The stream we are reading from
	 * @return The player being read
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Player readPlayer(BufferedReader br) throws IOException, InvalidLoadException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();

		final Time remainingTime = matchForTime(br, "time");
		final String name = matchForString(br, "name");
		final User user = User.hasWithName(name) ? User.getWithName(name) : quoridor.addUser(name);
		
		final int targetNumber = matchForInt(br, "target");
		final Direction direction = matchForEnum(br, "dir", Direction.class);
		
		final Player p = new Player(remainingTime, user, targetNumber, direction);

		final int numberOfWalls = matchForInt(br, "walls");
		for (int i = 0; i < numberOfWalls; ++i) {
			int wallId = matchForInt(br, "id");
			if (Wall.hasWithId(wallId)) {
				p.addWall(Wall.getWithId(wallId));
			} else {
				p.addWall(wallId);
			}
		}

		return p;
	}
	
	/**
	 * Retrieves a line with format: {@code $key:[+-]?\d+}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static int matchForInt(BufferedReader br, String key) throws IOException, InvalidLoadException {
		final String line = getNextUsefulLine(br);
		final String errMsg = "Invalid numeric format `" + line + "`";

		if (line == null || !line.matches('^' + key + ":[+-]?\\d+$")) {
			throw new InvalidLoadException(errMsg);
		}
		try {
		return Integer.parseInt(line.substring(key.length() + 1));
		} catch (NumberFormatException ex) {
			throw new InvalidLoadException(errMsg, ex);
	}
	}

	/**
	 * Retrieves a line with format: {@code $key:\d{1,2}:\d{1,2}:\d{1,2}}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Time matchForTime(BufferedReader br, String key) throws IOException, InvalidLoadException {
		final String line = getNextUsefulLine(br);
		final String errMsg = "Invalid time format `" + line + "`";

		if (line == null || !line.matches('^' + key + ":\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			throw new InvalidLoadException(errMsg);
		}

		final String[] seq = line.substring(key.length() + 1).split(":");
		try {
		return new Time(
				Integer.parseInt(seq[0]),
				Integer.parseInt(seq[1]),
				Integer.parseInt(seq[2]));
		} catch (NumberFormatException ex) {
			throw new InvalidLoadException(errMsg, ex);
		}
	}
	
	/**
	 * Retrieves a line with format: {@code $key:.*}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static String matchForString(BufferedReader br, String key) throws IOException, InvalidLoadException {
		final String line = getNextUsefulLine(br);
		final String errMsg = "Invalid string format `" + line + "`";

		if (line == null || !line.matches('^' + key + ":.*$")) {
			throw new IllegalArgumentException(errMsg);
		}
		return line.substring(key.length() + 1);
	}
	
	/**
	 * Retrieves a line with format: {@code $key:{enum values}}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @param type The enum with values (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails
	 * @throws InvalidLoadException If format is wrong
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static <T extends Enum<T>> T matchForEnum(BufferedReader br, String key, Class<T> type) throws IOException, InvalidLoadException {
		try {
		return Enum.valueOf(type, matchForString(br, key).trim());
		} catch (InvalidLoadException | IllegalArgumentException ex) {
			// Rethrow with better error message
			throw new InvalidLoadException("Invalid enum values of type " + type.getSimpleName(), ex);
		}
	}
	
	/**
	 * Retrieves the next line that is not a comment (starts with '#') nor empty
	 * 
	 * @param br The stream we are reading from
	 * @return next line that is not a comment nor empty; can still return null
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static String getNextUsefulLine(BufferedReader br) throws IOException {
		String line;
		while ((line = br.readLine()) != null) {
			if (!line.isEmpty() && line.charAt(0) != '#') {
				// Found it!
				break;
			}
		}
		return line;
	}

	/**
	 *
	 * @returns the player associated with the current turn
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
	 * @returns the player associated with the name, null if no such player exists
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
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// There isn't even a game!
			return null;
		}

		final Game game = quoridor.getCurrentGame();

		switch (color) {
			case WHITE:
				return fromPlayer(game.getWhitePlayer());
			case BLACK:
				return fromPlayer(game.getBlackPlayer());
			default:
				return null;
		}
	}

	/**
	 * Converts a Player to TOPlayer
	 *
	 * @param p the Player
	 * @returns the corresponding TOPlayer
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static TOPlayer fromPlayer(Player p) {
		final TOPlayer player = new TOPlayer();
		player.setName(p.getUser().getName());

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

		// TODO: Figure out how to grab a wall

		return player;
	}

	/**
	 *
	 * @param name The name of the desired player
	 * @returns the player associated with the name, null if no such player exists
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
	 * @return a new wall candidate (wall move)
	 */
	public static TOWallCandidate createWallCandidateAtInitialPosition() {
		throw new UnsupportedOperationException("Query method create-wall-candidate is not implemented yet");
	}
	
	/**
	 * @author alixe delabrousse
	 * 
	 * @param direction
	 * @param row
	 * @param column
	 * @return
	 */
	
	public static TOWallCandidate createWallCandidateAtPosition(Orientation direction, int row, int column) {
		throw new UnsupportedOperationException("Query method create-wall-candidate-at-position is not implemented yet");
	}
	
	/**
	 * @author alixe delabrousse
	 * 
	 * @return the current wall candidate, the current wall move
	 */
	public static TOWallCandidate getWallCandidate() {
		throw new UnsupportedOperationException("Query method get-wall-candidate is not implemented yet");
	}

	/**
	 *
	 * @param name The name of the player who owns the walls
	 * @returns the walls associated to the player, null if no such player exists
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
	 * @returns the walls associated to the player, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static List<TOWall> getWallsOwnedByPlayer(Color color) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		if (!quoridor.hasCurrentGame()) {
			// There isn't even a game!
			return null;
		}

		final Game game = quoridor.getCurrentGame();

		final Player p;
		switch (color) {
			case WHITE:
				p = game.getWhitePlayer();
				break;
			case BLACK:
				p = game.getBlackPlayer();
				break;
			default:
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
	 * @returns the corresponding TOWall
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
		}
		return toWall;
	}
	
	/**
	 * @author alixe delabrousse
	 * 
	 * @param nrow
	 * @param ncolumn
	 * @param TOWall
	 */
	
	public static void updateWallPosition(TOWall wall, int nrow, int ncolumn) {
		throw new UnsupportedOperationException("Query method update-wall-position is not implemented yet");
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
	 * 
	 * @returns the current wall grabbed by the player
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 */
	public static TOWall getCurrentGrabbedWall() {
		throw new UnsupportedOperationException("Query method get-current-grabbed-wall is not implemented yet");
	}
	
	/**
	 * 
	 * @returns the current wall candidate
	 * 
	 * @author Mohamed Mohamed (260855731)
	 * 
	 */
	public static TOWallCandidate getCurrentWallCandidate() {
		throw new UnsupportedOperationException("Query method get-current-wall-candidate is not implemented yet");
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
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @author alixe delabrousse
	 * 
	 * @return TOPlayer - returns the player associated with the black pawn
	 */
	public static TOPlayer getBlackPlayer()	{
		throw new UnsupportedOperationException();
	}

}// end QuoridorController

