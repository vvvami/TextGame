package net.vami.interactables.items;

import net.vami.interactables.interactions.damagetypes.FireDamage;

public class ExcaliburItem extends ItemHoldable {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(new FireDamage())
                .baseDamage(15));
    }
}
