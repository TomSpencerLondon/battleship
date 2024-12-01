package battleship.ui.validation;

import battleship.ui.Coordinate;

import java.util.List;

public class CoordinateValidator implements PlacementValidator {
    @Override
    public boolean validate(List<Coordinate> coordinates) {
        if (coordinates.size() != 2 || !coordinates.get(0).isValid() || !coordinates.get(1).isValid()) {
            throw new ShipPlacementException("Invalid coordinates. Try again:");
        }

        return true;
    }
}
