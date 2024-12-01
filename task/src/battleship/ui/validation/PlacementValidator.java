package battleship.ui.validation;


import battleship.ui.Coordinate;

import java.util.List;
import java.util.Objects;

public interface PlacementValidator {
    boolean validate(List<Coordinate> coordinates);
}
