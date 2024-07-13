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
            if (!(ally.getBrain() == null)) {
                ally.getBrain().executeTask(ally);
            }
        }
    }

    public static void Generate() {
        new Werewolf("Friend", new Entity.Attributes()).setEnemy(false);
        new ExcaliburItem("Excalibur", new ItemHoldable.Attributes());
    }

}
