package battleship.validation;

public class PositionOccupiedException extends RuntimeException {
    public PositionOccupiedException(String message) {
        super(message);
    }
}
