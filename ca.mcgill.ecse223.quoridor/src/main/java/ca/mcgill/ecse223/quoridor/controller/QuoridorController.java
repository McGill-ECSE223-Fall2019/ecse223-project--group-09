package ca.mcgill.ecse223.quoridor.controller;

<<<<<<< HEAD
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
=======
import java.lang.UnsupportedOperationException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
>>>>>>> 7358450468b5c404f4232f3ea0c7149f4cd69197

/**
 * This is the controller class for the Quoridor application
 * 
 * @author GROUP-9 (Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, Alixe Delabrousse)
 * @version 04-10-2019
 */
<<<<<<< HEAD

public class String QuoridorController  {
	
	/**
	 * This method allows the user to create a new username.
	 * 
	 * @param String user; 
	 * @return String user; 
	 * 
	 * @author Ada Andrei
	 */

	public static selectUsername() {

		Quoridor quoridor = QuoridorApplication.getQuoridor(); 

		java.lang.UnsupportedOperationExceptiontry {
			throw new UnsupportedOperationException;
		} catch (java.lang.UnsupportedOperationException e)

	}
	
	/**
	 * This method allows the user to select a username already created. 
	 *  
	 * @param String user; 
	 * @return String user; 
	 * 
	 * @author Ada Andrei
	 */
	
	public static selectUsername() {

		Quoridor quoridor = QuoridorApplication.getQuoridor(); 

		try {
			throw new UnsupportedOperationException;
		} catch (java.lang.UnsupportedOperationException e)

	}
	
	/**
	 * This set the total thinking time (minutes and seconds) enforced for both players.
	 * 
	 * @param int time;
	 * @return Time remainingTime; 
	 * 
	 * @author Ada Andrei
	 */
	
	public static setTime() {

		Quoridor quoridor = QuoridorApplication.getQuoridor(); 

		try {
			throw new UnsupportedOperationException;
		} catch (java.lang.UnsupportedOperationException e)

		return remainingTime; 

=======


public class QuoridorController {

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
>>>>>>> 7358450468b5c404f4232f3ea0c7149f4cd69197
	}
}

