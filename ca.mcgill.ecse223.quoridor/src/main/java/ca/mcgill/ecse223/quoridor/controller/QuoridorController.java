package ca.mcgill.ecse223.quoridor.controller;

import java.lang.UnsupportedOperationException;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This is the controller class for the Quoridor application
 * 
 * @author GROUP-9 (Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, Alixe Delabrousse)
 * @version 04-10-2019
 */


public class QuoridorController {
	
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
	 * 
	 * @author alixe delabrousse 
	 * 
	 * @param player
	 * @param direction
	 * 
	 * This method allows the player to grab one if its walls from its remaining walls pile.
	 * This method enables the use of RotateWall, DropWall, and MoveWall.
	 * 
	 * 
	 */

	/**
	 * This method allows the user to create a new username or choose an existing one. 
	 * 
	 * @param String user;  
	 * 
	 * @author Ada Andrei
	 */

	public static void selectUsername(String user) throws UnsupportedOperationException{
		if (true) {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * This set the total thinking time (minutes and seconds) enforced for both players.
	 * 
	 * @param int remainingTime; 
	 * 
	 * @author Ada Andrei
	 */
	
	public static void setTime(int remainingTime) throws UnsupportedOperationException {
		if (true) {
			throw new UnsupportedOperationException();
		}
	}


	/**
 	* 
 	* @author alixe delabrousse 
	* 
 	* @param player
 	* @param direction
 	* 
	* This method allows the player to grab one if its walls from its remaining walls pile.
 	* This method enables the use of RotateWall, DropWall, and MoveWall.
 	* 
 	* 
 	*/
	
	public void grabWall(Player player, Direction direction) {
		throw new UnsupportedOperationException();
		
	}
	
	
	/**
	 * 
	 * @author alixe delabrousse
	 * 
	 * @param wall
	 * @param destination
	 * 
	 * This methods allows you to place your wall on the board.
	 * 
	 * 
	 */
	
	public void moveWall(Wall wall, GamePosition destination) {
		throw new UnsupportedOperationException();
		
	}
	
	/**
	 * @author Group 9
	 * 
	 * @param move
	 * 
	 * 
	 * 
	 */
	
	public void validatePosition(Move move) {
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
		throw new UnsupportedOperationException("Method Save Position is not implemented yet");

//		final File file = new File(filePath);
//		if (file.exists() && !overwriteIfExists) {
//			// File exists but user does not want to
//			// overwrite the file, so we are done
//			return false;
//		}
//		
//		try (final Writer writer = new FileWriter(file)) {
//			savePosition(writer);
//		}
//		return true;
	}

	/**
	 * Writes out the current board
	 *
	 * @param destination The stream we are writing to
	 * @throws IOException If writing operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void savePosition(Writer destination) throws IOException {
		throw new UnsupportedOperationException("Helper method Save Position is not implemented yet");
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
		throw new UnsupportedOperationException("Method Load Position is not implemented yet");

//		try (final Reader reader = new FileReader(filePath)) {
//			return loadPosition(reader);
//		}
	}
	
	/**
	 * Reads in a previously saved board
	 *
	 * @param source The stream we are reading from
	 * @returns true if positions are valid, false if positions are not
	 * @throws IOException If reading operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void loadPosition(Reader source) throws IOException {
		throw new UnsupportedOperationException("Helper method Load Position is not implemented yet");
	}

	/**
	 *
	 * @returns the player associated with the current turn
	 *
	 * @author Paul Teng (260862906)
	 */
	public static TOPlayer getPlayerOfCurrentTurn() {
		throw new UnsupportedOperationException("Query method get-player-of-current-turn is not implemented yet");
	}

	/**
	 *
	 * @param name The name of the desired player
	 * @returns the player associated with the name, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static TOPlayer getPlayerByName(String name) {
		throw new UnsupportedOperationException("Query method get-player-by-name is not implemented yet");
	}

	/**
	 *
	 * @param name The name of the player who owns the walls
	 * @returns the walls associated to the player, null if no such player exists
	 *
	 * @author Paul Teng (260862906)
	 */
	public static List<TOWall> getWallsOwnedByPlayer(String name) {
		throw new UnsupportedOperationException("Query method get-walls-owned-by-player is not implemented yet");
	}

	/**
	 *
	 * @returns the number of walls in stock of player with white pawn
	 *
	 * @author Paul Teng (260862906)
	 */
	public static int getWhiteWallsInStock() {
		throw new UnsupportedOperationException("Query method get-white-walls-in-stock is not implemented yet");
	}

	/**
	 *
	 * @returns the number of walls in stock of player with black pawn
	 *
	 * @author Paul Teng (260862906)
	 */
	public static int getBlackWallsInStock() {
		throw new UnsupportedOperationException("Query method get-black-walls-in-stock is not implemented yet");
	}

}// end QuoridorController

