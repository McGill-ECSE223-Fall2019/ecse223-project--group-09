package ca.mcgill.ecse223.quoridor.controller;

/**
 * This is the exception thrown when an illegal move is attempted
 *
 * @author Paul Teng (260862906)
 */
public class IllegalPawnMoveException extends RuntimeException {

    public IllegalPawnMoveException(String errorMessage) {
        super(errorMessage);
    }
}
