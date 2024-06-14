package net.vami.interactables.ai;

import net.vami.game.interactions.Action;
import net.vami.game.interactions.DamageType;
import net.vami.game.world.Game;
import net.vami.game.world.Node;
import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Werewolf;
import net.vami.interactables.items.ItemHoldable;

import java.util.ArrayList;

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
        new Werewolf("Enemy", new Entity.Attributes().level(1).maxHealth(1));

    }

}
