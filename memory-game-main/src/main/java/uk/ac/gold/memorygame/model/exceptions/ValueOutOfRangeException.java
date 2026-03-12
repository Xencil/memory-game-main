package uk.ac.gold.memorygame.model.exceptions;

public class ValueOutOfRangeException
        extends IllegalArgumentException {
    public ValueOutOfRangeException(String errorMessage) {
        super(errorMessage);
    }
}
