package battleship;

import battleship.domain.Board;
import battleship.domain.Position;
import battleship.domain.ShipType;
import battleship.ui.*;
import battleship.validation.PositionOccupiedException;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the game board

        Board board = new Board();

        printGame(board);
        // Get ship coordinates from the user
        Scanner scanner = new Scanner(System.in);

        for (ShipType ship : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getName(), ship.getCells());
            placeShip(ship, scanner, board);
        }

        System.out.println("The game starts!");

        printGame(board);

        System.out.println("Take a shot!");

        takeShot(scanner, board);

    }

    private static void placeShip(ShipType ship, Scanner scanner, Board board) {
        Coordinate start, end;
        int length;

        // Loop until ship is placed correctly
        while (true) {
            try {
                List<Coordinate> coordinates = getCoordinatesWithRetry(scanner, ship, board);
                start = coordinates.get(0);
                end = coordinates.get(1);
            } catch (ShipPlacementException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Calculate the ship's length
            length = calculateShipLength(start, end);

            // If the length is correct, break out of the loop
            if (length == ship.getCells()) {
                break;
            }

            // Otherwise, prompt for a retry
            System.out.println("Error! Wrong length of the " + ship.getName() + ". Try again:");
        }

        // Try to place the ship
        try {
            board.occupy(new Position(start.getRow(), start.getColumn()), new Position(end.getRow(), end.getColumn()));
            printGame(board);
        } catch (PositionOccupiedException | ShipPlacementException e) {
            System.out.println(e.getMessage());
            placeShip(ship, scanner, board); // Retry if the position is occupied
        }
    }

    private static List<Coordinate> getCoordinatesWithRetry(Scanner scanner, ShipType ship, Board board) {
        List<Coordinate> coordinates = null;
        while (coordinates == null) {
            try {
                coordinates = getValidCoordinates(ship, scanner, board);
            } catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage() + " Try again:");
            }
        }
        return coordinates;
    }

    private static void takeShot(Scanner scanner, Board board) {
        while(true) {
            String input = scanner.nextLine();
            Coordinate coordinate = new Coordinate(input);

            if (!coordinate.isValid()) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            board.registerShot(new Position(coordinate.getRow(), coordinate.getColumn()));
            printGame(board);

            switch (board.lastShotResult()) {
                case HIT:
                    System.out.println("You hit a ship!");
                    break;
                case MISS:
                    System.out.println("You missed!");
            }

            break;
        }
    }

    // Method to get valid coordinates for the ship placement
    private static List<Coordinate> getValidCoordinates(ShipType ship, Scanner scanner, Board board) {
        List<PlacementValidator> validationStrategies = List.of(
                new CoordinateValidator(),
                new LengthValidator(),
                new LocationValidator(),
                new ProximityValidator()
        );

        // Keep requesting input until it's valid
        while (true) {
            String input = scanner.nextLine();
            List<String> coordinatesInput = Arrays.stream(input.split(" ")).toList();
            List<Coordinate> coordinates = new ArrayList<>();

            // Convert input to Coordinates
            for (String coordinate : coordinatesInput) {
                coordinates.add(new Coordinate(coordinate)); // Now using Coordinate class
            }

            boolean isValid = true;
            for (PlacementValidator strategy : validationStrategies) {
                if (!strategy.validate(board, ship, coordinates)) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                return coordinates;
            } else {
                throw new InvalidCoordinateException("Invalid coordinates or placement.");
            }
        }
    }


    private static void printGame(Board board) {
        for (int i = 1; i <= 10; i++) {
            if (i == 1) {
                System.out.print("  " + i + " ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                Position position = new Position(i, j);
                if (board.isHit(position)) {
                    System.out.print("X ");
                } else if (board.isMiss(position)) {
                    System.out.print("M ");
                } else if (board.hasShipIn(position)) {
                    System.out.print("O ");
                } else {
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
    }

    private static List<Integer> getIndices(String coordinate) {
        int row = coordinate.charAt(0) - 'A';
        int col = Integer.parseInt(coordinate.substring(1)) - 1;
        return List.of(row, col);
    }

    private static int calculateShipLength(Coordinate start, Coordinate end) {
        if (start.getRow() == end.getRow()) {
            // Same row, different columns
            return Math.abs(end.getColumn() - start.getColumn()) + 1;
        } else {
            // Same column, different rows
            return Math.abs(end.getRow() - start.getRow()) + 1;
        }
    }

}
