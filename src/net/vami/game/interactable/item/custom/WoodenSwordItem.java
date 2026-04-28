package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.Statuses;
import net.vami.game.interactable.item.ItemHoldable;

public class WoodenSwordItem extends ItemHoldable {
    public WoodenSwordItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(1)
                .damageType(DamageTypes.BLUNT));
    }

    public WoodenSwordItem(Attributes attributes) {
        this("Wooden Sword", attributes);
    }

    public WoodenSwordItem() {
        this(new Attributes());
    }

}
