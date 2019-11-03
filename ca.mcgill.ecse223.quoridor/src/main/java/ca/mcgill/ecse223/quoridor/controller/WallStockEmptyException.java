package ca.mcgill.ecse223.quoridor.controller;

public class WallStockEmptyException extends RuntimeException{
	
	public WallStockEmptyException (String message) {
		super(message);
	}
	
	public WallStockEmptyException(String message, Throwable previousTrace) {
        super(message, previousTrace);
    }
	
}
