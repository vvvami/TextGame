package net.vami.interactables.ai;

import net.vami.game.world.Node;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Werewolf;

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
