package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;

public class RockItem extends Item implements ItemUseable {
    public RockItem() {

    }

    @Override
    public void onUse(Interactable source, ItemInstance item) {
        Entity owner = (Entity) source;
        if (owner.hasTarget()) {
            Interactable interactable = owner.getTarget();
            interactable.hurt(owner, owner.getDamage(), DamageTypes.BLUNT);
        }
    }
}
