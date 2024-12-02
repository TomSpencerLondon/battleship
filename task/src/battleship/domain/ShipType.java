package battleship.domain;

public enum ShipType {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5), BATTLESHIP("Battleship", 4), SUBMARINE("Submarine", 3), CRUISER("Cruiser", 3), DESTROYER("Destroyer", 2);

    private final String name;
    private final int cells;

    ShipType(String name, int cells) {
        this.name = name;
        this.cells = cells;
    }

    public String getName() {
        return name;
    }

    public int getCells() {
        return cells;
    }

    public boolean isValidLength(int length) {
        return length == cells;
    }

    public int getLength() {
        return cells;
    }
}
