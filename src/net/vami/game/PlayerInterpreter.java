package net.vami.game;

import net.vami.interactables.Interactable;

import java.util.Arrays;
import java.util.Scanner;

public class PlayerInterpreter {

    public static boolean read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(Main.ANSI_BLUE + "> " + Main.ANSI_RESET);
        String fullAction = scanner.nextLine();
        return actionInput(fullAction);

    }


    private static boolean actionInput(String input) {
        String[] inputArr = input.toLowerCase()
                .split("\\s+");
        Node node = Game.getCurrentNode();
        Interactable target = null;
        switch (inputArr.length) {
            case 2:
                target = node.stringToInteractable(inputArr[1]);

            case 1:
                if (target == null) {
                    break;
                }
                Action action = Action.synonymToAction.get(inputArr[0]);
                return target.receiveAction(Game.player, action);
            default: break;
        }
        return false;
    }
}
