package battleship.ui;

import battleship.domain.Board;
import battleship.domain.ShipType;

import java.util.List;

public class LengthValidator implements PlacementValidator {

    @Override
    public boolean validate(Board board, ShipType ship, List<Coordinate> coordinates) {
        List<Integer> start = coordinates.get(0).indices();
        List<Integer> end = coordinates.get(1).indices();
        int length = calculateShipLength(start, end);

        if (!ship.isValidLength(length)) {
            throw new ShipPlacementException(String.format("Error! Wrong length of the %s! Try again:", ship.getName()));
        }
        return true;
    }
}

