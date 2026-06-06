package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;

public class PurifiedPowderItem extends Item implements ItemUseable {

    public PurifiedPowderItem(String name) {
        super(name);
    }

    public PurifiedPowderItem() {
        this("Purified Powder");
    }


    @Override
    public void onUse(Interactable source, ItemInstance item) {
        source.addModifier(new Modifier("purified_powder", ModifierType.HEALING, 1));
        if (source instanceof Entity entity) {
            entity.removeInventoryItem(item);
        }
    }
}
