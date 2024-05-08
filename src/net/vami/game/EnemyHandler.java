package net.vami.game;

import net.vami.interactables.Entity;

public class EnemyHandler {

    public static void enemyAction() {

    }

    public static void enemyTargeting(Entity source) {
        if (source.getTarget() == null || !source.getTarget().isEnded()) {
            for (Entity ally : Node.getAllies()) {
                source.setTarget(ally);
            }
        }
    }

    public static void Generate(Position position) {
        Entity enemy = null;
        Game.getCurrentNode().getInteractables().add(enemy);
    }

}
