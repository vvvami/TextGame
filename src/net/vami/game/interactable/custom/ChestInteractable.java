package net.vami.game.interactable.custom;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.Item;
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
            Game.display(sourceEntity, "%s opens %s.%n", sourceEntity.getName(), this.getName());
            for (UUID uuid : inventory) {
                Item item = (Item) Interactable.getInteractableFromID(uuid);
                Game.playSound(this, Sound.ITEM_DROP, 65);
                sourceEntity.addItem(item);
                Game.display(sourceEntity, "%s has obtained %s! %n", sourceEntity.getName(), item.getDisplayName());
            }
        }
        return super.receiveTake(source);
    }

    @Override
    public boolean receiveAttack(Interactable source) {
        ArrayList<Item> dropList = new ArrayList<>();
        for (UUID uuid : inventory) {
            Item item = (Item) Interactable.getInteractableFromID(uuid);
            dropList.add(item);
        }

        for (Item item : dropList) {
            item.setPos(this.getPos());
            if (item == dropList.getLast()) {
                Game.playSound(this, Sound.ITEM_DROP, 65);
            }
            Game.display(this,"%s was cracked open by %s! %n", this.getName(), source.getDisplayName());
            Game.display(this,"%s dropped %s. %n", this.getName(), item.getDisplayName());
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

    public ChestInteractable addItem(Item item) {
        inventory.add(item.getID());
        return this;
    }

    public ChestInteractable roll(LootPool pool, int rolls) {
        if (rolls <= 0) return this;
        for (int i = 0; i < rolls; i++) {
            addItem(pool.choose());
        }
        return this;
    }

}
