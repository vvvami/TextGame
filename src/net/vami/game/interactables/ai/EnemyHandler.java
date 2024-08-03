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

        new Werewolf("Enemy1", new Entity.Attributes().level(4)).setEnemy(true);
//        new Werewolf("Enemy2", new Entity.Attributes().level(4)).setEnemy(true);
//        new Werewolf("Enemy3", new Entity.Attributes().level(4)).setEnemy(true);

    }

}
