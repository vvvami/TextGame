package net.vami.interactables.ai;

import net.vami.game.world.Position;
import net.vami.interactables.interactions.Action;
import net.vami.interactables.interactions.DamageType;
import net.vami.game.world.Node;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.entities.Player;
import net.vami.interactables.entities.Werewolf;
import net.vami.interactables.items.ExcaliburItem;
import net.vami.interactables.items.ItemHoldable;

public class AllyHandler {

    public static void allyAction() {
        for (Entity ally : Node.getAllies()) {
            if (!(ally instanceof Player)) {
                allyTargeting(ally);
                if (ally.hasTarget()) {
                    ally.applyAction(ally.getTarget(), Action.ATTACK);
                }
            }
        }
    }

    public static void allyTargeting(Entity source) {
        if (source.getTarget() == null) {
            for (Entity enemy : Node.getEnemies()) {
                source.setTarget(enemy);
            }
        }
    }

    public static void Generate() {
        new Werewolf("Friend", new Entity.Attributes()).setEnemy(false);
        ExcaliburItem test = new ExcaliburItem("Excalibur", new ItemHoldable.Attributes().baseDamage(100).damageType(DamageType.ICE));
        System.out.println(test.getBaseDamage());
        System.out.println(test.getDamageType().getName());

    }

}
