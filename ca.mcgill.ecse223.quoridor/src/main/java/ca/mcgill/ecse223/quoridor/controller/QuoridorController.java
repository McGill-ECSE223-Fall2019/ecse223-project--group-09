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
	 * @returns true if position is valid, false otherwise
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

		final List<Move> moves = game.getMoves();

		if (moves.isEmpty()) {
			// Generate players without moves
			// *still need to preserve round ordering*
			final Player p = game.getCurrentPosition().getPlayerToMove();
			if (p.hasGameAsWhite()) {
				pw.println("W:");
				pw.println("B:");
			} else {
				pw.println("B:");
				pw.println("W:");
	}
			return;
		}

		final boolean whitePlayerStart = moves.get(0).getPlayer().hasGameAsWhite();
		final StringBuilder whitePlayerMoves = new StringBuilder("W: ");
		final StringBuilder blackPlayerMoves = new StringBuilder("B: ");

		// Popuate the moves
		for (final Move move : moves) {
			// Select the correct builder to append to
			final StringBuilder appendTo;
			if (move.getPlayer().hasGameAsWhite()) {
				appendTo = whitePlayerMoves;
			} else {
				appendTo = blackPlayerMoves;
			}

			// Appeend the move!
			// - column is written as [a-i]
			// - row is written as [1-9]
			// - wall is denoted by 'v' or 'h'
			final Tile target = move.getTargetTile();
			appendTo.append('a' - 1 + target.getColumn()).append(target.getRow());
			if (move instanceof WallMove) {
				final Direction dir = ((WallMove) move).getWallDirection();
				switch (dir) {
					case Vertical:
						appendTo.append('v');
						break;
					case Horizontal:
						appendTo.append('h');
						break;
					default:
						throw new IOException("Unrecognized wall move direction: " + dir.name());
			}
		}
			appendTo.append(", ");
	}

		// Normalize endings: by now an appended builder
		// will end in ", " which is super ugly...
		final int whiteTextLength = whitePlayerMoves.length();
		if (whiteTextLength > 3) {
			whitePlayerMoves.delete(whiteTextLength - 2, whiteTextLength);
	}
		final int blackTextLength = blackPlayerMoves.length();
		if (blackTextLength > 3) {
			blackPlayerMoves.delete(blackTextLength - 2, blackTextLength);
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
	private static Orientation fromDirection(final Direction dir) {
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

