package net.vami.game.interactables.items.equipables;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Modifier;
import net.vami.game.interactables.interactions.ModifierType;
public class ArthuurosEyeItem extends ItemEquipable {
    public ArthuurosEyeItem(String name, int durability) {
        super(name, durability);
    }

    @Override
    public boolean onEquip(Entity owner) {
        owner.addModifier(new Modifier("eyeOfArthuuros", ModifierType.DAMAGE, 10));
        return super.onEquip(owner);
    }

    @Override
    public boolean onUnequip(Entity owner) {
        owner.removeModifier("eyeOfArthuuros");
        return super.onUnequip(owner);
    }
}
