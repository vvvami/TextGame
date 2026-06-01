package net.vami.game.interactable.ai;

import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.world.Direction;
import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.interactable.Interactable;

import java.awt.event.WindowEvent;


public class PlayerHandler {

    public static boolean inputToAction(String input) {
        if (input.equalsIgnoreCase("quit")) {
            Game.getFrame().dispatchEvent(new WindowEvent(Game.getFrame(), WindowEvent.WINDOW_CLOSING));
            Game.endGame = true;
            return false;
        }

        String[] inputArr = input.toLowerCase()
                .split("\\s+");
        Node node = Game.getCurrentNode();
        Action action = Action.synonymToAction.get(inputArr[0]);
        if (action == null) {
            return false;
        }
        return switch (action) {
            case MOVEMENT -> movementSwitch(inputArr, action);
            case TAKE -> takeItemSwitch(input, node, action);
            case SAVE, RESIST -> Game.player.receiveAction(Game.player, action);
            case ATTACK, ABILITY -> combatSwitch(input, node, action);
            case DROP, EQUIP, USE -> interactItemSwitch(input, action);
        };
    }

    private static Interactable getActionTarget(String input, Node node) {
        input = input.substring(input.indexOf(' ') + 1);
        return node.stringToInteractable(input);
    }

    private static boolean noValidTarget(String input) {
        input = input.substring(input.indexOf(' ') + 1);
        return input.isBlank();
    }

    private static boolean combatSwitch(String input, Node node, Action action) {
        Interactable target = getActionTarget(input, node);


        if (target == null && noValidTarget(input)) {

            if (action == Action.ABILITY && Game.player.getAbility().isSupport()) {
                target = Game.player;
            } else {
                target = Game.getEnemyContext();
                Game.setEnemyContext(null);
            }
        }

        if (target == null) {
            return false;
        }

        return target.receiveAction(Game.player, action);
    }

    private static boolean takeItemSwitch(String input, Node node, Action action) {
        Interactable target = getActionTarget(input, node);

        input = input.substring(input.indexOf(' ') + 1);
        if (input.equals("all")) {
            int i = 0;
            for (Interactable interactable : Game.player.getNode().getInteractables()) {
                if (interactable instanceof ItemInstance item) {
                    item.receiveAction(Game.player, action);
                }
                i++;
            }
            Game.setItemContext(null);
            return i > 0;
        }

        if (target == null) {
            if (Game.getItemContext() == null) {
                return false;
            }
            target = Game.getItemContext();
            Game.setItemContext(null);
        }

        return target.receiveAction(Game.player, action);
    }

    private static boolean interactItemSwitch(String input, Action action) {
        ItemInstance target = null;
        input = input.substring(input.indexOf(' ') + 1);

        for (ItemInstance item : Game.player.getInventory()) {
            if (item.getName().equalsIgnoreCase(input)) {
                target = item;
            }
        }

        for (ItemInstance item : Game.player.getEquippedItems()) {
            if (item.getName().equalsIgnoreCase(input)) {
                target = item;
            }
        }

        if (target == null && Game.player.hasHeldItem()) {
            target = Game.player.getHeldItem();
        }

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
            return Game.player.receiveAction(Game.player, action);
        }
        return false;
    }

}
