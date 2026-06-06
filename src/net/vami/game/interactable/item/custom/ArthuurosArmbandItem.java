package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.ItemEquipable;
import net.vami.game.interactable.item.ItemInstance;

public class ArthuurosArmbandItem extends ItemEquipable {
    public ArthuurosArmbandItem(String name) {
        super(name);
    }

    public ArthuurosArmbandItem() {
        this("Arthuuros' Armband");
    }

    @Override
    public ItemInstance create() {
        ItemInstance item = super.create();
        item.addModifier(new Modifier("arthuurosArmband_health", ModifierType.MAX_HEALTH, -5));
        item.addModifier(new Modifier("arthuurosArmband_damage", ModifierType.DAMAGE, 5));
        return item;
    }
}
