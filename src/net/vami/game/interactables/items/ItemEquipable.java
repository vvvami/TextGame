package net.vami.game.interactables.items;

import net.vami.game.display.text.TextFormatter;
import net.vami.game.world.Position;
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
            entitySource.addInventoryItem(this);
            System.out.printf("%s has no room for %s, so they stashed it instead. %n",
                    entitySource.getName(), this.getDisplayName());
            return false;
        }

        entitySource.getEquippedItems().add(this);
        System.out.printf("%s equips %s. %n", entitySource.getName(), this.getDisplayName());
        return super.receiveEquip(source);
    }
}
