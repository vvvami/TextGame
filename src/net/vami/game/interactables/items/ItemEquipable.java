package net.vami.game.interactables.items;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;

public abstract class ItemEquipable extends Item {
    public ItemEquipable(String name) {
        super(name);
    }

    public ItemEquipable() {
        this(null);
    }

    @Override
    public boolean receiveEquip(Interactable source) {
        if (!(source instanceof Entity entitySource)) {
            return false;
        }

        if (entitySource.getEquippedItems().size() >= entitySource.getMaxEquipSlots()) {
            TextUtil.display(this,"%s cannot equip more items. %n",
                    entitySource.getName());
            return false;
        }

        if (entitySource.hasItemEquipped(this)) {
            this.onUnequip();
            entitySource.removeEquippedItem(this);
            entitySource.addInventoryItem(this);
            Game.playSound(this.getOwner(), Sound.ITEM_DROP, 65);
            TextUtil.display(this,"%s stashes %s. %n", entitySource.getName(), this.getDisplayName());
            return false;
        }

        entitySource.addEquippedItem(this);
        Game.playSound(this.getOwner(), Sound.ITEM_EQUIP, 65);
        TextUtil.display(this,"%s equips %s. %n", entitySource.getName(), this.getDisplayName());
        return super.receiveEquip(source);
    }
}
