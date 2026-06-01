package net.vami.game.interactable.custom;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.display.Segmental;
import net.vami.game.display.sound.Sounds;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.loot.LootPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChestInteractable extends Interactable {
    private List<UUID> inventory = new ArrayList<>();
    public ChestInteractable(String name) {
        super(name);
        addReceivableAction(Action.TAKE);
        addReceivableAction(Action.ATTACK);
    }

    @Override
    public boolean receiveTake(Interactable source) {
        if (source instanceof Entity sourceEntity) {
            Display.showText(sourceEntity, "%s opens %s.%n", sourceEntity, this);
            for (UUID uuid : inventory) {
                ItemInstance item = (ItemInstance) Interactable.getInteractableFromID(uuid);
                sourceEntity.addInventoryItem(item);
                Display.display(sourceEntity,
                        new Segmental("%s has obtained %s! %n", sourceEntity, item),
                        Sounds.ITEM_DROP, 65);
            }
        }
        return super.receiveTake(source);
    }

    @Override
    public boolean receiveAttack(Interactable source) {
        ArrayList<ItemInstance> dropList = new ArrayList<>();
        for (UUID uuid : inventory) {
            ItemInstance item = (ItemInstance) Interactable.getInteractableFromID(uuid);
            dropList.add(item);
        }

        Display.showText(source, "%s was cracked open by %s! %n", this, source);

        for (ItemInstance item : dropList) {
            item.receiveDrop(source);
        }

        this.remove();
        return true;
    }

    public List<UUID> getInventory() {
        return inventory;
    }

    public void setInventory(List<UUID> inventory) {
        this.inventory = inventory;
    }

    public ChestInteractable addItem(ItemInstance item) {
        inventory.add(item.getID());
        return this;
    }

    public ChestInteractable roll(LootPool pool, int rolls) {
        if (rolls <= 0) return this;
        for (int i = 0; i < rolls; i++) {
            addItem(((Item) pool.choose()).create());
        }
        return this;
    }

}
