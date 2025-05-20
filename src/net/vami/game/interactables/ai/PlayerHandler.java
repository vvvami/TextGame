package net.vami.game.interactables.ai;

import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.items.Item;
import net.vami.game.world.Direction;
import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.interactables.Interactable;

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

    private static boolean combatSwitch(String input, Node node, Action action) {
        Interactable target;
        input = input.substring(input.indexOf(' ') + 1);
        target = node.stringToInteractable(input);

        boolean isSelfCastSpell = Game.player.getAbility().isSelfCast();

        if (isSelfCastSpell) {
            target = Game.player;
        }

        if (target == null) {
           return false;
        }

        return target.receiveAction(Game.player, action);
    }

    private static boolean takeItemSwitch(String input, Node node, Action action) {
        Interactable target;
        input = input.substring(input.indexOf(' ') + 1);
        target = node.stringToInteractable(input);

        if (target == null) {
            return false;
        }
        return target.receiveAction(Game.player, action);
    }

    private static boolean interactItemSwitch(String input, Action action) {
        Item target = null;
        input = input.substring(input.indexOf(' ') + 1);

        for (Item item : Game.player.getInventory()) {
            if (item.getName().equalsIgnoreCase(input)) {
                target = item;
            }
        }

        for (Item item : Game.player.getEquippedItems()) {
            if (item.getName().equalsIgnoreCase(input)) {
                target = item;
            }
        }

        if (Game.player.hasHeldItem() &&
                Game.player.getHeldItem().getName().equalsIgnoreCase(input)) {

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
