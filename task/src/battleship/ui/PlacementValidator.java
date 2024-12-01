package battleship.ui;


import battleship.domain.Board;
import battleship.domain.ShipType;

import java.util.List;
import java.util.Objects;

public interface PlacementValidator {
    boolean validate(Board board, ShipType ship, List<Coordinate> coordinates);

    default int calculateShipLength(List<Integer> start, List<Integer> end) {
        if (Objects.equals(start.get(0), end.get(0))) {
            return Math.abs(end.get(1) - start.get(1)) + 1;
        } else {
            return Math.abs(end.get(0) - start.get(0)) + 1;
        }
    }
}
