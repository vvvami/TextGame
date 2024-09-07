package net.vami.game.interactables.ai;

import net.vami.game.interactables.interactions.abilities.PrayAbility;
import net.vami.game.interactables.interactions.damagetypes.BluntDamage;
import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.interactions.damagetypes.IceDamage;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Werewolf;
import net.vami.game.interactables.items.ExcaliburItem;
import net.vami.game.interactables.items.ItemHoldable;

public class AllyHandler {

    public static void allyAction(Node node) {
        for (Entity ally : node.getAllies()) {
            if (!(ally.getBrain() == null)) {
                ally.getBrain().selectTask(ally);
            }
        }
    }

    public static void Generate() {

        new ExcaliburItem("Excalibur1", new ItemHoldable.Attributes()
                .baseDamage(10));
        new ExcaliburItem("Excalibur2", new ItemHoldable.Attributes()
                .baseDamage(200)
                .damageType(new IceDamage()));
    }

}
