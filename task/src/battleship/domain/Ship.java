package battleship.domain;
import java.util.List;
public class Ship {
    private final List<Position> positions;

    public Ship(List<Position> positions) {
        this.positions = positions;
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public boolean isSunk(List<Position> hitPositions) {
        return positions.stream().allMatch(hitPositions::contains);
    }
}
