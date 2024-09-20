package net.vami.game.interactables.items;
import net.vami.game.display.text.TextFormatter;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.equipables.ItemEquipable;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.interactables.items.useables.UseableItem;

import java.util.UUID;

public class Item extends Interactable {

    private int durability;
    private UUID owner;

    public Item(String name) {
        super(name);
        if (this instanceof BreakableItem breakableItem) {
            this.durability = breakableItem.maxDurability();
        }
        this.addReceivableAction(Action.TAKE);
        this.addReceivableAction(Action.EQUIP);
        this.addReceivableAction(Action.DROP);
        this.addReceivableAction(Action.USE);
    }

    public Entity getOwner() {

        return (Entity) Interactable.getInteractableFromID(owner);
    }

    public void setOwner(Entity owner) {

        this.owner = owner.getID();
    }


    public int getDurability() {

        return durability;
    }

    public void setDurability(int durability) {

        this.durability = durability;
    }


    public void hurt(int amount) {
        durability -= amount;
        if (durability <= 0) {
            getOwner().removeFromInventory(this);
            getOwner().removeItem(this);
            annihilate();
            System.out.printf("%s has broken!%n", this.getDisplayName());
        }
    }

    @Override
    public boolean receiveEquip(Interactable source) {
        Entity entitySource = (Entity) source;
        entitySource.removeFromInventory(this);
        this.setOwner(entitySource);
        this.onEquip(entitySource);
        return true;
    }

    @Override
    public boolean receiveTake(Interactable source) {
        Entity entitySource = (Entity) source;
        entitySource.addInventoryItem(this);
        if (this instanceof ItemHoldable && !entitySource.hasHeldItem()) {
            this.receiveEquip(entitySource);
        } else {
            System.out.printf("%s takes %s. %n", entitySource.getName(), this.getDisplayName());
        }
        this.setOwner(entitySource);
        return true;
    }

    @Override
    public boolean receiveUse(Interactable source) {
        if (this instanceof UseableItem useableItem) {
                if (useableItem.useCondition()) {
                    useableItem.onUse();
                    return true;
                }
                else {
                    System.out.println(useableItem.failMessage());
                }
        }
        return false;
    }

    @Override
    public boolean receiveDrop(Interactable source) {
        Entity sourceEntity = (Entity) source;
        if ((this instanceof ItemHoldable
                && this == sourceEntity.getHeldItem())
                ||
                (this instanceof ItemEquipable itemEquipable
                && sourceEntity.hasItemEquipped(itemEquipable))) {

            this.onUnequip(sourceEntity);
        }
        sourceEntity.removeItem(this);
        this.setPos(sourceEntity.getPos());
        System.out.printf("%s has dropped %s. %n", sourceEntity.getDisplayName(), this.getDisplayName());
        return super.receiveDrop(source);
    }

    public boolean onEquip(Entity owner) {

        return true;
    }

    public boolean onUnequip(Entity owner) {

        return true;
    }

    public boolean turn() {

        return true;
    }

    public String getDisplayName() {
        return TextFormatter.purple(this.getName());
    }
}
