package ca.mcgill.ecse223.quoridor.controller;

import java.lang.UnsupportedOperationException;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;

/**
 * This is the controller class for the Quoridor application
 * 
 * @author GROUP-9 (Barry Chen, Mohamed Mohamed, Ada Andrei, Paul Teng, Alixe Delabrousse)
 *
 */


public class QuoridorController {
	
	/**
	 * 
	 * @author Barry Chen 
	 * 
	 * @param board
	 * 
	 * This method sets up a new game
	 * 
	 * 
	 */
	
	public Game startNewGame {
		
	}
	
	
	/**
	 * 
	 * @author Barry Chen 
	 * 
	 * @param board
	 * 
	 * This method initiates the board
	 * 
	 * 
	 */
	
	public void initiateBoard(Board B) {
		
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
	
	


}
