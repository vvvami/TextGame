package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.interactions.Modifier;
import net.vami.game.interactables.interactions.ModifierType;
import net.vami.game.interactables.items.ItemEquipable;

public class ArthuurosReapingArmband extends ItemEquipable {
    public ArthuurosReapingArmband(String name) {
        super(name);
        this.addModifier(new Modifier("reapingArmband_health", ModifierType.MAX_HEALTH, -5));
        this.addModifier(new Modifier("reapingArmband_damage", ModifierType.DAMAGE, 5));

    }
}
