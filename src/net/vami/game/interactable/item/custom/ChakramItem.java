package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemHoldable;

public class ChakramItem extends ItemHoldable {
    public ChakramItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(1)
                .damageType(DamageTypes.SHARP));
    }

    public ChakramItem(Attributes attributes) {
        this("Chakram", attributes);
    }

    public ChakramItem() {
        this(new Attributes());
    }

    @Override
    public void onHit(Interactable owner, Interactable target, DamageType damageType, float amount) {
        if (Math.random() <= 0.5) {
            target.hurt(owner, amount, damageType);
        }

        super.onHit(owner, target, damageType, amount);
    }
}
