package battleship.ui;

public class ShipPlacementException extends RuntimeException {
    public ShipPlacementException(String message) {
        super(message); // Pass the error message to the Exception class
    }
}

