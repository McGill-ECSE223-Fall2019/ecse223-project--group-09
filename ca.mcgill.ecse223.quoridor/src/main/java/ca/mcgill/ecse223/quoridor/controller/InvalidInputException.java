package ca.mcgill.ecse223.quoridor.controller;

/**
 * This is the exception thrown when an invalid input is received. 
 * 
 * @author Ada Andrei
 */

public class InvalidInputException extends Exception {
	
	private static final long serialVersionUID = -5633915762703837868L;
	
	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}

}
