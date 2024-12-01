package battleship.ui;

import battleship.domain.Board;
import battleship.domain.Position;
import battleship.domain.ShipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProximityValidator implements PlacementValidator {
    private final Board board;

    public ProximityValidator(Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(List<Coordinate> coordinates) {
        return validate(coordinates, board);
    }

    public boolean validate(List<Coordinate> coordinates, Board board) {
        List<Integer> start = coordinates.get(0).indices();
        List<Integer> end = coordinates.get(1).indices();

        if (isPositionOccupied(board, start) || isPositionOccupied(board, end)) {
            throw new ShipPlacementException("Error! You placed it too close to another one. Try again:");
        }

        // Check if all parts of the ship's range are occupied
        for (Position position : getShipParts(start, end)) {
            if (board.hasShipIn(position)) {
                throw new ShipPlacementException("Error! You placed it too close to another one. Try again:");
            }
        }

        return true; // No proximity issues
    }

    private boolean isPositionOccupied(Board board, List<Integer> position) {
        // Check if the given position is already occupied by another ship
        return board.hasShipIn(new Position(position.get(0), position.get(1)));
    }

    private List<Position> getShipParts(List<Integer> start, List<Integer> end) {
        int length = calculateShipLength(start, end);
        List<Position> parts = new ArrayList<>(length); // List of positions

        if (Objects.equals(start.get(0), end.get(0))) {
            // Horizontal ship
            int row = start.get(0);
            int colStart = Math.min(start.get(1), end.get(1));
            for (int i = 0; i < length; i++) {
                parts.add(new Position(row, colStart + i)); // Add Position to the list
            }
        } else {
            // Vertical ship
            int col = start.get(1);
            int rowStart = Math.min(start.get(0), end.get(0));
            for (int i = 0; i < length; i++) {
                parts.add(new Position(rowStart + i, col)); // Add Position to the list
            }
        }
        return parts;
    }

    int calculateShipLength(List<Integer> start, List<Integer> end) {
        if (Objects.equals(start.get(0), end.get(0))) {
            return Math.abs(end.get(1) - start.get(1)) + 1;
        } else {
            return Math.abs(end.get(0) - start.get(0)) + 1;
        }
    }
}
