package net.vami.game.interactables.items.equipables;

import net.vami.game.interactables.items.Item;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;

public abstract class ItemEquipable extends Item {
    public ItemEquipable(String name, int durability) {

        super(name, durability);
    }

    @Override
    public boolean receiveEquip(Interactable source) {
        if (!(source instanceof Entity entitySource)) {
            return false;
        }

        if (entitySource.getEquippedItems().size() >= entitySource.getMaxEquipSlots()) {
            System.out.printf("%s cannot equip more items. %n",
                    entitySource.getName());
            return false;
        }

        if (entitySource.hasItemEquipped(this)) {
            this.onUnequip(entitySource);
            entitySource.removeItem(this);
            entitySource.addInventoryItem(this);
            System.out.printf("%s stashes %s. %n", entitySource.getName(), this.getDisplayName());
            return false;
        }

        entitySource.getEquippedItems().add(this);
        System.out.printf("%s equips %s. %n", entitySource.getName(), this.getDisplayName());
        return super.receiveEquip(source);
    }
}
