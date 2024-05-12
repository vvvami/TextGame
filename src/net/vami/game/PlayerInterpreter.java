package net.vami.game;

import net.vami.interactables.Interactable;

import java.util.Scanner;

public class PlayerInterpreter {

    public static boolean read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose your next action: ");
        String fullAction = scanner.nextLine();
        actionInput(fullAction);

        return false;
    }


    private static void actionInput(String input) {
        String[] inputArr = input.toLowerCase()
                .split("\\s+");

        Node node = Game.getCurrentNode();
        Interactable target = null;
        switch (inputArr.length) {
            case 2:
                target = node.stringToInteractable(inputArr[1]);
            case 1:
                Action action = Action.synonymToAction.get(inputArr[0]);
                assert target != null;
                target.receiveAction(Game.player, action);
                break;
            default:
        }
    }
}
