package battleship.ui;

import battleship.domain.Board;
import battleship.domain.ShipType;

import java.util.List;
import java.util.Objects;

public class LocationValidator implements PlacementValidator {

    @Override
    public boolean validate(Board board, ShipType ship, List<Coordinate> coordinates) {
        List<Integer> start = coordinates.get(0).indices();
        List<Integer> end = coordinates.get(1).indices();

        if (!Objects.equals(start.get(0), end.get(0)) && !Objects.equals(start.get(1), end.get(1))) {
            throw new ShipPlacementException("Error! Wrong ship location! Try again:");
        }
        return true;
    }
}

