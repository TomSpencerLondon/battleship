package battleship.domain;

import battleship.ui.Coordinate;
import battleship.validation.AdjacentChecker;
import battleship.validation.AdjacentCheckerFactory;
import battleship.validation.PositionOccupiedException;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Position> occupiedPositions;
    private final List<Position> hitPositions;
    private final List<Position> missPositions;
    private final List<Ship> ships;
    private ShotResult lastShotResult;

    public Board() {
        occupiedPositions = new ArrayList<>();
        hitPositions = new ArrayList<>();
        missPositions = new ArrayList<>();
        ships = new ArrayList<>();
        this.lastShotResult = ShotResult.NONE;
    }

    public List<Position> occupiedPositions() {
        return occupiedPositions;
    }

    public boolean hasShipIn(Position position) {
        return occupiedPositions.contains(position);
    }

    public ShotResult registerShot(Position position) {
        if (!hasShipIn(position)) {
            missPositions.add(position);
            lastShotResult = ShotResult.MISS;
            return lastShotResult;
        }

        hitPositions.add(position);
        lastShotResult = ShotResult.HIT;

        if (isSunk(position)) {
            lastShotResult = handleSunkShip();
        }

        return lastShotResult;
    }

    private ShotResult handleSunkShip() {
        return allShipsSunk() ? ShotResult.ALL_SHIPS_SUNK : ShotResult.SUNK;
    }


    public void occupy(Position first, Position second) {
        Direction direction = first.row() == second.row() ? Direction.HORIZONTAL : Direction.VERTICAL;

        AdjacentChecker checker = AdjacentCheckerFactory.createChecker(this, direction);
        checker.checkForAdjacentPositions(first, second);

        List<Position> newShipPositions = new ArrayList<>();

        if (first.row() == second.row()) {
            newShipPositions = occupyHorizontally(first, second);
        } else {
            newShipPositions = occupyVertically(first, second);
        }

        ships.add(new Ship(newShipPositions));
    }


    private List<Position> occupyVertically(Position first, Position second) {
        List<Position> positions = new ArrayList<>();
        int rowStart = Math.min(first.row(), second.row());
        int rowEnd = Math.max(first.row(), second.row());  // Get the ending row
        int col = first.col();

        for (int i = rowStart; i <= rowEnd; i++) {
            Position vertical = new Position(rowStart++, col);
            if (occupiedPositions.contains(vertical)) {
                throw new PositionOccupiedException("You placed it too close to another one. Try again:");
            }
            occupiedPositions.add(vertical);
            positions.add(vertical);
        }

        return positions;
    }

    private List<Position> occupyHorizontally(Position first, Position second) {
        List<Position> positions = new ArrayList<>();
        int row = first.row();
        int colStart = Math.min(first.col(), second.col());
        int colEnd = Math.max(first.col(), second.col());  // Get the ending column

        for (int i = colStart; i <= colEnd; i++) {
            Position horizontal = new Position(row, colStart++);
            if (occupiedPositions.contains(horizontal)) {
                throw new PositionOccupiedException("You placed it too close to another one. Try again:");
            }
            occupiedPositions.add(horizontal);
            positions.add(horizontal);
        }

        return positions;
    }

    public boolean isSunk(Position position) {
        for (Ship ship : ships) {
            if (ship.contains(position) && ship.isSunk(hitPositions)) {
                return true;
            }
        }

        return false;
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk(hitPositions)) {
                return false;
            }
        }

        return true;
    }

    public boolean isHit(Position position) {
        return hitPositions.contains(position);
    }

    public boolean isMiss(Position position) {
        return missPositions.contains(position);
    }

    public boolean isWithinBounds(Coordinate coordinate) {
        int row = coordinate.getRow();
        int column = coordinate.getColumn();

        return row >= 0 && row < 10 && column >= 0 && column < 10; // Assuming 10x10 board
    }

}
