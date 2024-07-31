package net.vami.game.interactables.ai;

import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.entities.Werewolf;
import net.vami.game.interactables.items.ExcaliburItem;
import net.vami.game.interactables.items.ItemHoldable;

public class AllyHandler {

    public static void allyAction() {
        for (Entity ally : Node.getAllies()) {
            if (!(ally.getBrain() == null)) {
                ally.getBrain().chooseTask(ally);
            }
        }
    }

    public static void Generate() {
        new Werewolf("Friend", new Entity.Attributes()).setEnemy(false);
        new ExcaliburItem("Excalibur", new ItemHoldable.Attributes());
    }

}
