package battleship.ui;

import battleship.domain.Board;
import battleship.domain.ShipType;

import java.util.List;

public class CompositePlacementValidator implements PlacementValidator {
    private final List<PlacementValidator> placementValidators;

    public CompositePlacementValidator(Board board, ShipType shipType) {
        this.placementValidators = List.of(
                new LengthValidator(shipType),
                new ProximityValidator(board),
                new CoordinateValidator(),
                new LocationValidator()
        );

    }

    @Override
    public boolean validate(List<Coordinate> coordinates) {
        boolean isValid = placementValidators.stream()
                .allMatch(validator -> validator.validate(coordinates)); // Returns true only if all validators pass

        if (!isValid) {
            throw new InvalidCoordinateException("Invalid coordinates or placement.");
        }

        return true; // Return true if all validations pass
    }
}
