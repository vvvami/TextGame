package net.vami.interactables.ai;

import net.vami.game.interactions.Action;
import net.vami.game.interactions.DamageType;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Werewolf;
import net.vami.interactables.items.ItemHoldable;

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

    public static void Generate(Position position) {
        new Werewolf("Foe",1);
        new ItemHoldable("Excalibur", null, position,
                1,5, DamageType.FIRE);
    }

}
