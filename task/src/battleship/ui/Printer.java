package battleship.ui;

import battleship.domain.Board;
import battleship.domain.Position;
import battleship.domain.ShipType;

public class Printer {

    public static void printBoard(Board board, BoardView view) {
        // Print column numbers
        for (int i = 1; i <= 10; i++) {
            if (i == 1) {
                System.out.print("  " + i + " ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        // Print each row of the board
        for (int i = 0; i < 10; i++) {
            System.out.print((char) ('A' + i) + " ");  // Row letters
            for (int j = 0; j < 10; j++) {
                Position position = new Position(i, j);

                if (board.isHit(position)) {
                    System.out.print("X ");
                } else if (board.isMiss(position)) {
                    System.out.print("M ");
                } else {
                    if (view == BoardView.FULL_VIEW && board.hasShipIn(position)) {
                        System.out.print("O ");
                    } else {
                        System.out.print("~ ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void printGameStart() {
        System.out.println("The game starts!");
    }

    public static void printTakeShot() {
        System.out.println("Take a shot!");
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printShipPrompt(ShipType ship) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getName(), ship.getCells());
    }

    public static void printError(String errorMessage) {
        System.out.println("Error! " + errorMessage);
    }
}
