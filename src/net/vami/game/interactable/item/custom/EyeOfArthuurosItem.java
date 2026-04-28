package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.ItemEquipable;

public class EyeOfArthuurosItem extends ItemEquipable {
    public EyeOfArthuurosItem(String name) {
        super(name);
        this.addModifier(new Modifier("eyeOfArthuuros", ModifierType.DAMAGE, 5));
    }

    public EyeOfArthuurosItem() {
        this("Eye of Arthuuros");
    }

    @Override
    public void turn() {
        super.turn();
        this.getOwner().hurt(null, 1, DamageTypes.BLEED);
    }
}
