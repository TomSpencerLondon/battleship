package battleship.ui;

import battleship.domain.Board;
import battleship.domain.ShipType;

import java.util.List;

public class CoordinateValidator implements PlacementValidator {
    @Override
    public boolean validate(List<Coordinate> coordinates) {
        if (coordinates.size() != 2 || !coordinates.get(0).isValid() || !coordinates.get(1).isValid()) {
            throw new ShipPlacementException("Error! Invalid coordinates. Try again:");
        }

        return true;
    }
}
