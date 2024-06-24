package net.vami.interactables.items;

import net.vami.interactables.interactions.DamageType;

public class ExcaliburItem extends ItemHoldable {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .durability(1)
                .damageType(DamageType.FIRE)
                .baseDamage(20));
    }
}
