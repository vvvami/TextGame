package net.vami.game.interactables.items.holdables;

import net.vami.game.interactables.interactions.damagetypes.FireDamage;
import net.vami.game.interactables.items.BreakableItem;

public class ExcaliburItem extends ItemHoldable implements BreakableItem {
    public ExcaliburItem(String name, Attributes attributes) {
        super(name, attributes
                .damageType(new FireDamage())
                .baseDamage(15));
    }

    @Override
    public int maxDurability() {
        return 100;
    }
}
