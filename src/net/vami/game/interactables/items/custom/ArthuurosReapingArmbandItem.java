package net.vami.game.interactables.items.custom;

import net.vami.game.interactables.interactions.modifier.Modifier;
import net.vami.game.interactables.interactions.modifier.ModifierType;
import net.vami.game.interactables.items.ItemEquipable;

public class ArthuurosReapingArmbandItem extends ItemEquipable {
    public ArthuurosReapingArmbandItem(String name) {
        super(name);
        this.addModifier(new Modifier("reapingArmband_health", ModifierType.MAX_HEALTH, -5));
        this.addModifier(new Modifier("reapingArmband_damage", ModifierType.DAMAGE, 5));

    }
}
