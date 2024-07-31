package net.vami.game.interactables.ai;

import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Werewolf;

public class EnemyHandler {

    public static void enemyAction() {
        for (Entity enemy : Node.getEnemies()) {

            enemy.getBrain().chooseTask(enemy);
        }
    }

    public static void Generate() {

        new Werewolf("Enemy", new Entity.Attributes().level(4)).setEnemy(true);
    }

}
