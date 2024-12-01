package battleship;

import battleship.domain.Board;
import battleship.domain.Position;
import battleship.domain.ShipType;
import battleship.ui.BoardView;
import battleship.ui.Coordinate;
import battleship.ui.Printer;
import battleship.ui.validation.CompositePlacementValidator;
import battleship.ui.validation.InvalidCoordinateException;
import battleship.ui.validation.PlacementValidator;
import battleship.ui.validation.ShipPlacementException;
import battleship.validation.PositionOccupiedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Printer.printBoard(board, BoardView.FULL_VIEW);

        Scanner scanner = new Scanner(System.in);

        for (ShipType ship : ShipType.values()) {
            Printer.printShipPrompt(ship);
            placeShip(ship, scanner, board);
        }

        Printer.printGameStart();
        Printer.printBoard(board, BoardView.FOG_OF_WAR);

        Printer.printTakeShot();
        takeShot(scanner, board);

        Printer.printBoard(board, BoardView.FULL_VIEW);
    }

    private static void placeShip(ShipType ship, Scanner scanner, Board board) {
        Coordinate start, end;

        while (true) {
            try {
                List<Coordinate> coordinates = getCoordinatesWithRetry(scanner, ship, board);
                start = coordinates.get(0);
                end = coordinates.get(1);
            } catch (ShipPlacementException e) {
                Printer.printError(e.getMessage());
                continue;
            }

            int length = calculateShipLength(start, end);

            if (ship.isValidLength(length)) {
                break;
            }

            Printer.printError("Wrong length of the " + ship.getName() + ". Try again:");
        }

        try {
            board.occupy(new Position(start.getRow(), start.getColumn()), new Position(end.getRow(), end.getColumn()));
            Printer.printBoard(board, BoardView.FULL_VIEW);
        } catch (PositionOccupiedException | ShipPlacementException e) {
            Printer.printError(e.getMessage());
            placeShip(ship, scanner, board);
        }
    }

    public static int calculateShipLength(Coordinate start, Coordinate end) {
        if (start.getRow() == end.getRow()) {
            // Same row, different columns (horizontal placement)
            return Math.abs(end.getColumn() - start.getColumn()) + 1;
        } else {
            // Same column, different rows (vertical placement)
            return Math.abs(end.getRow() - start.getRow()) + 1;
        }
    }

    private static List<Coordinate> getCoordinatesWithRetry(Scanner scanner, ShipType ship, Board board) {
        List<Coordinate> coordinates = null;

        while (coordinates == null) {
            try {
                coordinates = getValidCoordinates(ship, scanner, board);
            } catch (InvalidCoordinateException e) {
                Printer.printError(e.getMessage() + " Try again:");
            }
        }

        return coordinates;
    }

    private static void takeShot(Scanner scanner, Board board) {
        while (true) {
            String input = scanner.nextLine();
            Coordinate coordinate = new Coordinate(input);

            if (!coordinate.isValid()) {
                Printer.printError("You entered the wrong coordinates! Try again:");
                continue;
            }

            board.registerShot(new Position(coordinate.getRow(), coordinate.getColumn()));
            Printer.printBoard(board, BoardView.FOG_OF_WAR);

            switch (board.lastShotResult()) {
                case HIT:
                    Printer.printMessage("You hit a ship!");
                    break;
                case MISS:
                    Printer.printMessage("You missed!");
            }

            break;
        }
    }

    private static List<Coordinate> getValidCoordinates(ShipType ship, Scanner scanner, Board board) {
        PlacementValidator compositeValidator = new CompositePlacementValidator(board, ship);

        while (true) {
            String input = scanner.nextLine();
            List<String> coordinatesInput = Arrays.stream(input.split(" ")).toList();
            List<Coordinate> coordinates = new ArrayList<>();

            for (String coordinate : coordinatesInput) {
                coordinates.add(new Coordinate(coordinate));
            }

            if (compositeValidator.validate(coordinates)) {
                return coordinates;
            } else {
                throw new InvalidCoordinateException("Invalid coordinates or placement.");
            }
        }
    }
}
