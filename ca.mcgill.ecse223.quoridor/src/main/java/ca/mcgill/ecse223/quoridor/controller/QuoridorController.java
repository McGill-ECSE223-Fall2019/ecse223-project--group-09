package ca.mcgill.ecse223.quoridor.controller;

import java.lang.UnsupportedOperationException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

/**
 * This is the controller class for the Quoridor application
 * 
 * @author GROUP-9 (Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, Alixe Delabrousse)
 *
 */


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
	 * Writes out the current board
	 *
	 * @param destination The stream we are writing to
	 * @throws IOException If writing operation fails
	 * @author Paul Teng (260862906)
	 */
	public void savePosition(Writer destination) throws IOException {
		throw new UnsupportedOperationException("Save Position is not implemented yet");
	}
	
	/**
	 * Reads in a previously saved board
	 *
	 * @param source The stream we are reading from
	 * @throws IOException If reading operation fails
	 * @author Paul Teng (260862906)
	 */
	public void loadPosition(Reader source) throws IOException {
		throw new UnsupportedOperationException("Save Position is not implemented yet");
	}
}
