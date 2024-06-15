package net.vami.interactables.ai;

import net.vami.interactables.interactions.Action;
import net.vami.game.world.Node;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Werewolf;
import net.vami.interactables.interactions.DamageType;

public class EnemyHandler {

    public static void enemyAction() {
        for (Entity enemy : Node.getEnemies()) {
                enemyTargeting(enemy);
            if (enemy.hasTarget()) {
                enemy.applyAction(enemy.getTarget(), Action.ATTACK);
            }
        }
    }

    public static void enemyTargeting(Entity source) {
        if (source.getTarget() == null) {
            for (Entity ally : Node.getAllies()) {
                source.setTarget(ally);
            }
        }
    }

    public static void Generate() {
        Werewolf enemy = new Werewolf("Enemy", new Entity.Attributes().level(4));
        System.out.println(enemy.getHealth());
        System.out.println(enemy.getDefaultDamageType().getName());
    }

}
