package net.vami.game.interactables.ai;

import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Werewolf;

public class EnemyHandler {

    public static void enemyAction(Node node) {
        for (Entity enemy : node.getEnemies()) {
            enemy.getBrain().selectTask(enemy);
        }
    }

    public static void Generate() {
        new Werewolf("Gang", new Entity.Attributes()).setEnemy(true);
    }

}
