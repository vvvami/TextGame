package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.ItemEquipable;
import net.vami.game.interactable.item.ItemInstance;

public class EyeOfArthuurosItem extends ItemEquipable {
    public EyeOfArthuurosItem(String name) {
        super(name);
    }

    public EyeOfArthuurosItem() {
        this("Eye of Arthuuros");
    }

    @Override
    public void turn(ItemInstance item) {
        super.turn(item);
        item.getOwner().hurt(null, 1, DamageTypes.BLEED);
    }

    @Override
    public ItemInstance create() {
        ItemInstance item = super.create();
        item.addModifier(new Modifier("eyeOfArthuuros", ModifierType.DAMAGE, 8));
        return item;
    }
}
