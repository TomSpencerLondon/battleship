package battleship.domain;

import battleship.validation.AdjacentChecker;
import battleship.validation.AdjacentCheckerFactory;
import battleship.validation.PositionOccupiedError;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Position> occupiedPositions;
    private final List<Position> hitPositions;
    private final List<Position> missPositions;
    private ShotResult lastShotResult;

    public Board() {
        occupiedPositions = new ArrayList<>();
        hitPositions = new ArrayList<>();
        missPositions = new ArrayList<>();
        this.lastShotResult = ShotResult.NONE;
    }

    public List<Position> occupiedPositions() {
        return occupiedPositions;
    }

    public boolean hasShipIn(Position position) {
        return occupiedPositions.contains(position);
    }

    public ShotResult lastShotResult() {
        return lastShotResult;
    }

    public void registerShot(Position position) {
        if (hasShipIn(position)) {
            hitPositions.add(position);
            lastShotResult = ShotResult.HIT;
        } else {
            missPositions.add(position);
            lastShotResult = ShotResult.MISS;
        }
    }

    public void markAsHit(Position position) {
        if (!hitPositions.contains(position)) {
            hitPositions.add(position);
        }
    }

    public void markAsMiss(Position position) {
        if (!missPositions.contains(position)) {
            missPositions.add(position);
        }
    }

    public void occupy(Position first, Position second) {
        Direction direction = first.row() == second.row() ? Direction.HORIZONTAL : Direction.VERTICAL;

        AdjacentChecker checker = AdjacentCheckerFactory.createChecker(this, direction);
        checker.checkForAdjacentPositions(first, second);

        if (first.row() == second.row()) {
            occupyHorizontally(first, second);
        } else {
            occupyVertically(first, second);
        }
    }


    private void occupyVertically(Position first, Position second) {
        int rowStart = Math.min(first.row(), second.row());
        int rowEnd = Math.max(first.row(), second.row());  // Get the ending row
        int col = first.col();

        for (int i = rowStart; i <= rowEnd; i++) {
            Position vertical = new Position(rowStart++, col);
            if (occupiedPositions.contains(vertical)) {
                throw new PositionOccupiedError();
            }
            occupiedPositions.add(vertical);
        }
    }

    private void occupyHorizontally(Position first, Position second) {
        int row = first.row();
        int colStart = Math.min(first.col(), second.col());
        int colEnd = Math.max(first.col(), second.col());  // Get the ending column

        for (int i = colStart; i <= colEnd; i++) {
            Position horizontal = new Position(row, colStart++);
            if (occupiedPositions.contains(horizontal)) {
                throw new PositionOccupiedError();
            }
            occupiedPositions.add(horizontal);
        }
    }

    public boolean isHit(Position position) {
        return hitPositions.contains(position);
    }

    public boolean isMiss(Position position) {
        return missPositions.contains(position);
    }
}
