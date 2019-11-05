package ca.mcgill.ecse223.quoridor.controller;

public class NoGrabbedWallException extends RuntimeException {
	public NoGrabbedWallException(String message) {
		super(message);
	}
	
	public NoGrabbedWallException(String message, Throwable previousTrace) {
        super(message, previousTrace);
    }
}
