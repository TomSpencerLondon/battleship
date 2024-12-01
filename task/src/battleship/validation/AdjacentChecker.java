package battleship.validation;

import battleship.domain.Position;

import java.util.List;
import java.util.stream.IntStream;

public interface AdjacentChecker {
    void checkForAdjacentPositions(Position first, Position second);

    default boolean isAdjacentOccupied(Position position, List<Position> occupiedPositions) {
        int row = position.row();
        int col = position.col();

        // Check the 8 surrounding positions (horizontally, vertically, and diagonally)
        return IntStream.range(-1, 2)  // i = -1, 0, 1
                .anyMatch(i -> IntStream.range(-1, 2)  // j = -1, 0, 1
                        .filter(j -> !(i == 0 && j == 0))  // Skip the position itself
                        .anyMatch(j -> {
                            int newRow = row + i;
                            int newCol = col + j;
                            Position adjacent = new Position(newRow, newCol);
                            return isInBounds(newRow, newCol) && occupiedPositions.contains(adjacent);
                        }));
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < 10 && col >= 0 && col < 10;
    }
}

