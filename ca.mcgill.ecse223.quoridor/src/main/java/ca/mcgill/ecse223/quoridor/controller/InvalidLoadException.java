package ca.mcgill.ecse223.quoridor.controller;

/**
 * Exception that is thrown when load cannot process what it has read
 *
 * @author Paul Teng (260862906)
 */
public class InvalidLoadException extends Exception {

    public InvalidLoadException(String message) {
        super(message);
    }

    public InvalidLoadException(String message, Throwable previousTrace) {
        super(message, previousTrace);
    }
}