package ca.mcgill.ecse223.quoridor.controller;

import java.lang.UnsupportedOperationException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	 * This method is called when the player finishes his turn
	 * @author Group 9
	 * 
	 * @return boolean
	 * 
	 * 
	 */
	public static void switchCurrentPlayer() {
		throw new UnsupportedOperationException();

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
	public static boolean validatePawnPlacement(int row, int column) {
		throw new UnsupportedOperationException();
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
	public static boolean validateWallPlacement(int row, int column, Orientation orientation) {
		throw new UnsupportedOperationException();
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

		pw.printf("status:%s\n", game.getGameStatus());
		pw.printf("move:%s\n", game.getMoveMode());

		savePlayer(pw, game.getWhitePlayer());
		savePlayer(pw, game.getBlackPlayer());

		final GamePosition gamePosition = game.getCurrentPosition();
		pw.printf("id:%d\n", gamePosition.getId());
		saveTile(pw, gamePosition.getWhitePosition().getTile());
		saveTile(pw, gamePosition.getBlackPosition().getTile());
		if (gamePosition.getPlayerToMove() == game.getWhitePlayer()) {
			pw.printf("start:%s\n", Color.WHITE);
		} else {
			pw.printf("start:%s\n", Color.BLACK);
		}

		// TODO: Walls...
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
		pw.printf("dir:%s\n", d.getDirection());
	}
	
	/**
	 * Loads a previously saved board from a file 
	 * 
	 * @param filePath The file being read
	 * @returns true if positions are valid, false if positions are not
	 * @throws IOException If reading operation fails 
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static boolean loadPosition(String filePath) throws IOException {
		try (final Reader reader = new FileReader(filePath)) {
			return loadPosition(reader);
		} catch (IllegalArgumentException ex) {
			// Reading failed due to formatting
			throw new IOException(ex);
		}
	}
	
	/**
	 * Reads in a previously saved board
	 *
	 * Note: this method does not close any streams; it is the caller's
	 * responsibility to do so.
	 * 
	 * @param source The stream we are reading from
	 * @returns true if positions are valid, false if positions are not
	 * @throws IOException If reading operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static boolean loadPosition(Reader source) throws IOException {
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
			game = new Game(status, move, whitePlayer, blackPlayer, quoridor);
		} else {
			game = quoridor.getCurrentGame();
			game.setGameStatus(status);
			game.setMoveMode(move);
			game.setWhitePlayer(whitePlayer);
			game.setBlackPlayer(blackPlayer);
		}

		final int id = matchForInt(br, "id");
		final Tile whitePlayerTile = readTile(br);
		final Tile blackPlayerTile = readTile(br);

		PlayerPosition whitePosition = new PlayerPosition(whitePlayer, whitePlayerTile);
		PlayerPosition blackPosition = new PlayerPosition(blackPlayer, blackPlayerTile);
		final Color startingColor = matchForEnum(br, "start", Color.class);

		GamePosition gp = new GamePosition(
				id,
				whitePosition,
				blackPosition,
				startingColor == Color.WHITE ? whitePlayer : blackPlayer,
				game);

		// TODO: Walls...

		game.setCurrentPosition(gp);
		return true;
	}

	/**
	 * Reads in a tile position
	 * 
	 * @param br The stream we are reading from
	 * @return The tile being read
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Tile readTile(BufferedReader br) throws IOException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();

		final int row = matchForInt(br, "row");
		final int col = matchForInt(br, "col");

		// based off indexing scheme used in
		// CucumberStepDefintions#createAndStartGame(ArrayList<Player>)
		return quoridor.getBoard().getTile(row * 9 + col);
	}

	/**
	 * Reads in a player
	 * 
	 * @param br The stream we are reading from
	 * @return The player being read
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Player readPlayer(BufferedReader br) throws IOException {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();

		final Time remainingTime = matchForTime(br, "time");
		final String name = matchForString(br, "name");
		final User user = User.hasWithName(name) ? User.getWithName(name) : quoridor.addUser(name);

		final int targetNumber = matchForInt(br, "target");
		final Direction direction = matchForEnum(br, "dir", Direction.class);

		return new Player(remainingTime, user, targetNumber, direction);
	}
	
	/**
	 * Retrieves a line with format: {@code $key:[+-]?\d+}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static int matchForInt(BufferedReader br, String key) throws IOException {
		String line = br.readLine();
		if (line == null || !line.matches('^' + key + ":[+-]?\\d+$")) {
			throw new IllegalArgumentException("Invalid numeric format `" + line + "`");
		}
		return Integer.parseInt(line.substring(key.length() + 1));
	}

	/**
	 * Retrieves a line with format: {@code $key:\d{1,2}:\d{1,2}:\d{1,2}}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static Time matchForTime(BufferedReader br, String key) throws IOException {
		String line = br.readLine();
		if (line == null || !line.matches('^' + key + ":\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			throw new IllegalArgumentException("Invalid time format `" + line + "`");
		}
		final String[] seq = line.substring(key.length() + 1).split(":");
		return new Time(
				Integer.parseInt(seq[0]),
				Integer.parseInt(seq[1]),
				Integer.parseInt(seq[2]));
	}
	
	/**
	 * Retrieves a line with format: {@code $key:.*}
	 * 
	 * @param br The stream we are reading from
	 * @param key The key (stuff before colon)
	 * @return stuff after colon as integer
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static String matchForString(BufferedReader br, String key) throws IOException {
		String line = br.readLine();
		if (line == null || !line.matches('^' + key + ":.*$")) {
			throw new IllegalArgumentException("Invalid string format `" + line + "`");
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
	 * @throws IOException If reading operation fails, this includes if pattern is invalid
	 * 
	 * @author Paul Teng (260862906)
	 */
	private static <T extends Enum<T>> T matchForEnum(BufferedReader br, String key, Class<T> type) throws IOException {
		return Enum.valueOf(type, matchForString(br, key).trim());
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
	 * @returns the number of walls in stock of player with the specified color
	 *
	 * @author Paul Teng (260862906) and Alixe Delabrousse (260868412)
	 */
	
	public static int getWallsInStockOfColoredPawn(Color color) {
		throw new UnsupportedOperationException("Query method get-walls-in-stock-of-colored-pawn is not implemented yet");
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

