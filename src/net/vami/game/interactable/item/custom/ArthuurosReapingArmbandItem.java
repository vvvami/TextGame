package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.modifier.Modifier;
import net.vami.game.interactable.interaction.modifier.ModifierType;
import net.vami.game.interactable.item.ItemEquipable;

public class ArthuurosReapingArmbandItem extends ItemEquipable {
    public ArthuurosReapingArmbandItem(String name) {
        super(name);
        this.addModifier(new Modifier("reapingArmband_health", ModifierType.MAX_HEALTH, -5));
        this.addModifier(new Modifier("reapingArmband_damage", ModifierType.DAMAGE, 5));

    }

    public ArthuurosReapingArmbandItem() {
        this("Arthuuros' Reaping Armband");
    }
}
