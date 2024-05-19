package net.vami.game;

import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Werewolf;

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
        if (source.getTarget() == null || source.getTarget().isEnded()) {
            for (Entity ally : Node.getAllies()) {
                source.setTarget(ally);
            }
        }
    }

    public static void Generate(Position position) {
        Entity enemy = new Werewolf(position,1);
        Game.getCurrentNode().addInteractable(enemy);
    }

}
