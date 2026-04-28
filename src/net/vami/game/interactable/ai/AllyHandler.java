package net.vami.game.interactable.ai;

import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.interactable.entity.Entity;

public class AllyHandler {

    public static void allyAction(Node node) {
        for (Entity ally : node.getEntities()) {
            if (ally.isFriendlyTo(Game.player)
                    && !(ally.getBrain() == null)) {
                ally.getBrain().selectTask(ally);
            }
        }
    }
}
