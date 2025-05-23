package net.vami.game.interactables.custom;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.items.Item;
import net.vami.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chest extends Interactable {
    private List<UUID> inventory = new ArrayList<>();
    public Chest(String name) {
        super(name);
        addReceivableAction(Action.TAKE);
        addReceivableAction(Action.ATTACK);
    }

    @Override
    public boolean receiveTake(Interactable source) {
        if (source instanceof Entity sourceEntity) {
            TextUtil.display(sourceEntity, "%s opens %s.%n", sourceEntity.getName(), this.getName());
            for (UUID uuid : inventory) {
                Item item = (Item) Interactable.getInteractableFromID(uuid);
                Game.playSound(this, Sound.ITEM_DROP, 65);
                sourceEntity.addInventoryItem(item);
                TextUtil.display(sourceEntity, "%s has obtained %s! %n", sourceEntity.getName(), item.getDisplayName());
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
            TextUtil.display(this,"%s was cracked open by %s! %n", this.getName(), source.getDisplayName());
            TextUtil.display(this,"%s dropped %s. %n", this.getName(), item.getDisplayName());
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

    public Chest addToInventory(Item item) {
        inventory.add(item.getID());
        return this;
    }

}
