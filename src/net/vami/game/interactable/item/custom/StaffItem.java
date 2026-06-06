package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.attunement.ItemAttunable;

public class StaffItem extends ItemHoldable implements ItemAttunable {
    public StaffItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(1)
                .damageType(DamageTypes.BLUNT));
    }

    public StaffItem(Attributes attributes) {
        this("Staff", attributes);
    }

    public StaffItem() {
        this(new Attributes());
    }


}
