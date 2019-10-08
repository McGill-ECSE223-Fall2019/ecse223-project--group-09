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

/**
 * This is the controller class for the Quoridor application
 * 
 * @author GROUP-9 (Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, Alixe Delabrousse)
 * @version 04-10-2019
 */


public class QuoridorController {

	/**
	 * This method allows the user to create a new username.
	 * 
	 * @param String user;  
	 * 
	 * @author Ada Andrei
	 */

	public static void selectUsername(String user) {
		throw new UnsupportedOperationException;
		Quoridor quoridor = QuoridorApplication.getQuoridor();

		try {
			quoridor.addPlayer(user, false);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}
	
	/**
	 * This method allows the user to select a username already created. 
	 *  
	 * @param int index;
	 * 
	 * @author Ada Andrei
	 */
	
	public static selectUsername(int index) {
		throw new UnsupportedOperationException;

			

	}
	
	/**
	 * This set the total thinking time (minutes and seconds) enforced for both players.
	 * 
	 * @param int remainingTime; 
	 * 
	 * @author Ada Andrei
	 */
	
	public static void setTime(int remainingTime) throws InvalidInputException {
		throw new UnsupportedOperationException;
		String error = "";
		if (time <= 0) {
			error = "The time must be greater than zero. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		try {
		quoridor.addTime(remainingtime);
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
	 * 
	 * @author mohamed mohamed
	 * 
	 * @param wall
	 * @param destination
	 * 
	 * This methods allows you to place your wall on the board.
	 * 
	 * 
	 */
	
	public void rotateWall() {
		
	}
	
	/**
	 * 
	 * @author mohamed mohamed
	 * 
	 * @param wall
	 * @param destination
	 * 
	 * This methods allows you to place your wall on the board.
	 * 
	 * 
	 */
	public void dropWall() {
		
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
	 * @throws IOException If reading operation fails 
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void loadPosition(String filePath) throws IOException {
		try (final Reader reader = new FileReader(filePath)) {
			loadPosition(reader);
		}
	}
	
	/**
	 * Reads in a previously saved board
	 *
	 * @param source The stream we are reading from
	 * @throws IOException If reading operation fails
	 * 
	 * @author Paul Teng (260862906)
	 */
	public static void loadPosition(Reader source) throws IOException {
		throw new UnsupportedOperationException("Helper method Load Position is not implemented yet");
	}

}// end QuoridorController

