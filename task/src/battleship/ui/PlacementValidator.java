package battleship.ui;


import java.util.List;
import java.util.Objects;

public interface PlacementValidator {
    boolean validate(List<Coordinate> coordinates);
}
