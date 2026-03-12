package uk.ac.gold.memorygame.model.exceptions;

public class BoardStateException extends RuntimeException {
    public BoardStateException(String errorMessage) {
        super(errorMessage);
    }
}
