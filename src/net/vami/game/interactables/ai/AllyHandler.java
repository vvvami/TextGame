package net.vami.game.interactables.ai;

import net.vami.game.interactables.interactions.damagetypes.IceDamage;
import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.items.equipables.ArthuurosEyeItem;
import net.vami.game.interactables.items.useables.TearsOfArthuurosItem;
import net.vami.game.world.Node;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.holdables.ExcaliburItem;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.world.Position;

public class AllyHandler {

    public static void allyAction(Node node) {
        for (Entity ally : node.getAllies()) {
            if (!(ally.getBrain() == null)) {
                ally.getBrain().selectTask(ally);
            }
        }
    }

    public static void Generate() {
        Item.spawn(new ExcaliburItem("Excalibur", new ItemHoldable.Attributes()
                .baseDamage(10)));

    }

}
