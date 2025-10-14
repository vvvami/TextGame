package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.interactions.modifier.Modifier;
import net.vami.game.interactables.interactions.modifier.ModifierType;
import net.vami.game.interactables.interactions.damagetypes.BleedDamage;
import net.vami.game.interactables.items.ItemEquipable;

public class ArthuurosEyeItem extends ItemEquipable {
    public ArthuurosEyeItem(String name) {
        super(name);
        this.addModifier(new Modifier("eyeOfArthuuros", ModifierType.DAMAGE, 5));
    }

    public ArthuurosEyeItem() {
        this("Eye of Arthuuros");
    }

    @Override
    public void turn() {
        super.turn();
        this.getOwner().hurt(null, 1, BleedDamage.get);
    }
}
