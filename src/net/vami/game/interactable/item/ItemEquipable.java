package net.vami.game.interactable.item;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;

public abstract class ItemEquipable extends Item {
    public ItemEquipable(String name) {
        super(name);
    }

    public boolean onEquip(ItemInstance item) {
        return true;
    }

    public boolean onUnequip(ItemInstance item) {
        return true;
    }
}
