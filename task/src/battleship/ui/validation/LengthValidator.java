package battleship.ui.validation;

import battleship.domain.ShipType;
import battleship.ui.Coordinate;

import java.util.List;
import java.util.Objects;

public class LengthValidator implements PlacementValidator {

    private final ShipType ship;

    public LengthValidator(ShipType shipType) {
        this.ship = shipType;
    }

    @Override
    public boolean validate(List<Coordinate> coordinates) {
        List<Integer> start = coordinates.get(0).indices();
        List<Integer> end = coordinates.get(1).indices();
        int length = calculateShipLength(start, end);

        if (!ship.isValidLength(length)) {
            throw new ShipPlacementException(String.format("Wrong length of the %s! Try again:", ship.getName()));
        }
        return true;
    }

    private int calculateShipLength(List<Integer> start, List<Integer> end) {
        if (Objects.equals(start.get(0), end.get(0))) {
            return Math.abs(end.get(1) - start.get(1)) + 1;
        } else {
            return Math.abs(end.get(0) - start.get(0)) + 1;
        }
    }
}
