package net.vami.game;

import net.vami.interactables.Interactable;

import java.util.Arrays;
import java.util.Scanner;

public class PlayerInterpreter {

    public static boolean read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Main.ANSI_BLUE + "> " + Main.ANSI_RESET);
        String fullAction = scanner.nextLine();
        System.out.println("Full Action String: " + fullAction);
        return actionInput(fullAction);

    }


    private static boolean actionInput(String input) {
        String[] inputArr = input.toLowerCase()
                .split("\\s+");
        System.out.println("Input Array: " + Arrays.toString(inputArr));
        Node node = Game.getCurrentNode();
        Interactable target = null;
        switch (inputArr.length) {
            case 2:
                target = node.stringToInteractable(inputArr[1]);
                System.out.println("Case 2");

            case 1:
                if (target == null) {
                    System.out.println("Target is null");
                    break;
                }
                Action action = Action.synonymToAction.get(inputArr[0]);
                return target.receiveAction(Game.player, action);
            default: break;
        }
        return false;
    }
}
