package battleship.validation;

import battleship.domain.Board;
import battleship.domain.Position;

public class RowAdjacentChecker implements AdjacentChecker {
    private final Board board;

    public RowAdjacentChecker(Board board) {
        this.board = board;
    }

    @Override
    public void checkForAdjacentPositions(Position first, Position second) {
        int row = first.row();
        int colStart = Math.min(first.col(), second.col());
        int colEnd = Math.max(first.col(), second.col());

        for (int col = colStart; col <= colEnd; col++) {
            Position adjacent = new Position(row, col);
            if (isAdjacentOccupied(adjacent, board.occupiedPositions())) {
                throw new PositionOccupiedException("Error! You placed it too close to another one. Try again:"); // Error if adjacent position is occupied
            }
        }
    }
}
