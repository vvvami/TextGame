package net.vami.game.interactable.ai;

import net.vami.game.Game;
import net.vami.game.world.Node;
import net.vami.game.interactable.entity.Entity;

public class EnemyHandler {

    public static void enemyAction(Node node) {
        for (Entity enemy : node.getEntities()) {
            if (enemy.isFriendlyTo(Game.player)
                    || enemy.getBrain() == null) {
                continue;
            }
            enemy.getBrain().selectTask(enemy);
        }
    }

}
