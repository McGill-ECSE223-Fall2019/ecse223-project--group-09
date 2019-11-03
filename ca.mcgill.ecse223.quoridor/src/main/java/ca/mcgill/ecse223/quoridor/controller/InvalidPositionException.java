package ca.mcgill.ecse223.quoridor.controller;

public class InvalidPositionException extends RuntimeException {
	
	public InvalidPositionException(String message) {
		super(message);
	}
	
	public InvalidPositionException(String message, Throwable previousTrace) {
        super(message, previousTrace);
    }
	
}
