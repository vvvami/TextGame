package net.vami.game.interactables.items.holdables;

import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.items.ItemHoldable;

public class ExcaliburItem extends ItemHoldable {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(new FireDamage())
                .baseDamage(15));
    }
}
