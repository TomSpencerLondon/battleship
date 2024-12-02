package battleship;

import battleship.domain.PlayerTurn;
import battleship.domain.ShipType;
import battleship.domain.ShotResult;
import battleship.ui.Prompt;
import battleship.ui.PromptAction;

import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<Prompt, PromptAction> promptMap = new EnumMap<>(Prompt.class);
        promptMap.put(Prompt.PLAYER_1_SETUP, () -> System.out.println("Player 1, place your ships on the game field:"));
        promptMap.put(Prompt.PLAYER_2_SETUP, () -> System.out.println("Player 2, place your ships on the game field:"));
        promptMap.put(Prompt.PASS_MOVE, () -> System.out.println(
                "Press Enter and pass the move to another player"

        ));
        promptMap.put(Prompt.TAKE_SHOT, () -> System.out.println("Take a shot:"));
        promptMap.put(Prompt.INVALID_COORDINATES, () -> System.out.println("You entered the wrong coordinates! Try again:"));
        promptMap.put(Prompt.SHIP_PLACEMENT, new PromptAction() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("Use runWithShip for ship placement prompts.");
            }

            @Override
            public void runWithShip(ShipType ship) {
                System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells):");
            }
        });

        Map<ShotResult, Runnable> shotResultActions = new EnumMap<>(ShotResult.class);
        shotResultActions.put(ShotResult.HIT, () -> System.out.println("You hit a ship!"));
        shotResultActions.put(ShotResult.MISS, () -> System.out.println("You missed!"));
        shotResultActions.put(ShotResult.SUNK, () -> System.out.println("You sank a ship!"));
        shotResultActions.put(ShotResult.ALL_SHIPS_SUNK, () -> System.out.println("You sank the last ship. You won. Congratulations!"));

        GameMediator mediator = new GameMediator(promptMap, shotResultActions);

        Scanner scanner = new Scanner(System.in);
        mediator.setupPlayer(PlayerTurn.PLAYER_1, scanner, Prompt.PLAYER_1_SETUP);

        mediator.transitionToNextPlayer(scanner, Prompt.PASS_MOVE);

        mediator.setupPlayer(PlayerTurn.PLAYER_2, scanner, Prompt.PLAYER_2_SETUP);

        boolean gameOver = false;
        while (!gameOver) {
            mediator.promptForNextTurn(() -> System.out.println("Press Enter and pass the move to another player"));
            scanner.nextLine();
            gameOver = mediator.playTurn(scanner);
            mediator.switchTurn();
        }
    }
}
