package net.vami.interactables.ai;

import net.vami.game.interactions.Action;
import net.vami.game.interactions.DamageType;
import net.vami.game.world.Node;
import net.vami.game.world.Position;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Werewolf;
import net.vami.interactables.items.ItemHoldable;

public class AllyHandler {

    public static void allyAction() {
        for (Entity ally : Node.getAllies()) {
                enemyTargeting(ally);
            if (ally.hasTarget()) {
                ally.applyAction(ally.getTarget(), Action.ATTACK);
            }
        }
    }

    public static void enemyTargeting(Entity source) {
        if (source.getTarget() == null) {
            for (Entity enemy : Node.getEnemies()) {
                source.setTarget(enemy);
            }
        }
    }

    public static void Generate(Position position) {
        new Werewolf("Friend",1).setEnemy(false);
        new ItemHoldable("Excalibur", null, position,
                1,5, DamageType.FIRE);
    }

}
