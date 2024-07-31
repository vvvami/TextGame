package net.vami.game.interactables.ai;

import net.vami.game.display.text.TextFormatter;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.world.Direction;
import net.vami.game.world.Game;
import net.vami.game.world.Node;
import net.vami.game.interactables.Interactable;

import java.util.Scanner;

public class PlayerHandler {

    public static boolean read() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(TextFormatter.ANSI_BLUE + "> " + TextFormatter.ANSI_RESET);
        String fullAction = scanner.nextLine();
        return actionInput(fullAction);

    }



    private static boolean actionInput(String input) {
        String[] inputArr = input.toLowerCase()
                .split("\\s+");
        Node node = Game.getCurrentNode();
        Action action = Action.synonymToAction.get(inputArr[0]);
        if (action == null) {
            return false;
        }
        return switch (action) {
            case MOVEMENT -> movementSwitch(inputArr, action);
            case USE -> false;
            case TAKE, EQUIP -> interactionSwitch(input, node, action);
            case SAVE, RESIST -> Game.player.receiveAction(Game.player, action);
            case ATTACK, ABILITY -> combatSwitch(inputArr, node, action);
        };
    }

    private static boolean combatSwitch(String[] inputArr, Node node, Action action) {
        Interactable target = null;
        switch (inputArr.length) {
            case 2:
                target = node.stringToInteractable(inputArr[1]);

            case 1:
                if (target == null) {
                    break;
                }
                return target.receiveAction(Game.player, action);
            default: break;
        }
        return false;
    }

    private static boolean interactionSwitch(String input, Node node, Action action) {
        Interactable target;
        input = input.substring(input.indexOf(' ') + 1);
        target = node.stringToInteractable(input);
        if (target == null) {
            return false;
        }
        return target.receiveAction(Game.player, action);
    }

    private static boolean movementSwitch(String[] inputArr, Action action) {
        if (inputArr.length == 2) {
            Game.player.setDirection(Direction.getDirectionFromString(inputArr[1]));
            if (Game.player.getDirection() == null) {
                return false;
            }
            Game.player.receiveAction(Game.player, action);
        }
        return false;
    }

}
