package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.ItemEquipable;
import net.vami.game.interactable.item.ItemInstance;

public class VunnToothNecklaceItem extends ItemEquipable {
    public VunnToothNecklaceItem(String name) {
        super(name);
    }

    public VunnToothNecklaceItem() {
        this("Vunn's Tooth Necklace");
    }

    @Override
    public ItemInstance create() {
        ItemInstance item = super.create();
        item.addModifier(new Modifier("vunnToothNecklace", ModifierType.ARMOR, 1));
        return item;

    }
}
