package net.vami.game.interactables.items.equipables;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Modifier;
import net.vami.game.interactables.interactions.ModifierType;
import net.vami.game.interactables.interactions.damagetypes.BleedDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageType;

public class ArthuurosEyeItem extends ItemEquipable {
    public ArthuurosEyeItem(String name) {
        super(name);
        this.addModifier(new Modifier("eyeOfArthuuros", ModifierType.DAMAGE, 5));
    }

    @Override
    public boolean turn() {
        this.getOwner().hurt(null, 1, new BleedDamage());
        return super.turn();
    }
}
