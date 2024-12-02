package battleship.ui;

import java.util.List;
import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int column;

    // Constructor to create a Coordinate object from a string, e.g., "A1", "B3"
    public Coordinate(String input) {
        if (input.length() < 2) {
            throw new IllegalArgumentException("Invalid coordinate format");
        }

        char rowChar = input.charAt(0);
        int col = Integer.parseInt(input.substring(1));

        this.row = rowChar - 'A'; // Convert 'A'-'J' to 0-9
        this.column = col - 1;   // Convert 1-10 to 0-9
    }

    // Getter methods for row and column
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isValid() {
        // Row should be between 'A' and 'J' (0-9 column)
        // Column should be between 1 and 10 (1-based row)
        return column >= 0 && column <= 9 && row >= 0 && row <= 9;
    }

    // Method to get the indices as a list (row, column)
    public List<Integer> indices() {
        return List.of(row, column);
    }

    @Override
    public String toString() {
        // Return the coordinate in the format "A1", "B3"
        return String.format("%c%d", (char) ('A' + column), row + 1);
    }

    // You can also implement equals and hashCode if needed for comparisons
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

