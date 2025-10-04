package net.vami.game.interactables.ai;

import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;

public class AllyHandler {

    public static void allyAction(Node node) {
        for (Entity ally : node.getEntities()) {
            if (ally.isFriendlyTo(Game.player)
                    && !(ally.getBrain() == null)) {
                ally.getBrain().selectTask(ally);
            }
        }
    }

    public static void Generate() {


    }

}
