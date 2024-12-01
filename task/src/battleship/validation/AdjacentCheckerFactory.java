package battleship.validation;

import battleship.domain.Board;
import battleship.domain.Direction;

public class AdjacentCheckerFactory {

    // Factory method to create the appropriate checker based on the direction
    public static AdjacentChecker createChecker(Board board, Direction direction) {
        switch (direction) {
            case HORIZONTAL:
                return new RowAdjacentChecker(board); // Horizontal checker
            case VERTICAL:
                return new ColumnAdjacentChecker(board); // Vertical checker
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }
}

