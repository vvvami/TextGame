package net.vami.game.interactables.ai;

import net.vami.game.interactables.interactions.abilities.PrayAbility;
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
        new Werewolf("Friend1", new Entity.Attributes()
                .level(5)
                .ability(new PrayAbility())
                .defaultDamageType(new FireDamage()));
        new Werewolf("Friend2", new Entity.Attributes()
                .level(5)
                .ability(new PrayAbility())
                .defaultDamageType(new FireDamage()));
        new Werewolf("Friend3", new Entity.Attributes()
                .level(5)
                .ability(new PrayAbility())
                .defaultDamageType(new FireDamage()));

        new ExcaliburItem("Excalibur", new ItemHoldable.Attributes()
                .baseDamage(1)
                .damageType(new IceDamage()));
    }

}
