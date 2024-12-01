package battleship.ui.validation;

import battleship.ui.Coordinate;

import java.util.List;
import java.util.Objects;

public class LocationValidator implements PlacementValidator {

    @Override
    public boolean validate(List<Coordinate> coordinates) {
        List<Integer> start = coordinates.get(0).indices();
        List<Integer> end = coordinates.get(1).indices();

        if (!Objects.equals(start.get(0), end.get(0)) && !Objects.equals(start.get(1), end.get(1))) {
            throw new ShipPlacementException("Wrong ship location! Try again:");
        }
        return true;
    }
}

