package net.vami.game.interactables.items.holdables;

import net.vami.game.interactables.interactions.damagetypes.FireDamage;

public class ExcaliburItem extends ItemHoldable {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(new FireDamage())
                .baseDamage(15));
    }
}
