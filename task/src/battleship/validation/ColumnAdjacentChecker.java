package battleship.validation;

import battleship.domain.Board;
import battleship.domain.Position;

public class ColumnAdjacentChecker implements AdjacentChecker {
    private final Board board;

    public ColumnAdjacentChecker(Board board) {
        this.board = board;
    }

    @Override
    public void checkForAdjacentPositions(Position first, Position second) {
        int col = first.col();
        int rowStart = Math.min(first.row(), second.row());
        int rowEnd = Math.max(first.row(), second.row());

        for (int row = rowStart; row <= rowEnd; row++) {
            Position adjacent = new Position(row, col);
            if (isAdjacentOccupied(adjacent, board.occupiedPositions())) {
                throw new PositionOccupiedError(); // Error if adjacent position is occupied
            }
        }
    }
}

