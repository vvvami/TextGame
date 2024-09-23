package net.vami.game.interactables.items;
import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.util.TextUtil;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.equipables.ItemEquipable;
import net.vami.game.interactables.items.holdables.ItemHoldable;
import net.vami.game.interactables.items.useables.UseableItem;
import org.fusesource.jansi.AnsiConsole;

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
            getOwner().removeEquippedItem(this);
            annihilate();
            Game.playSound(Sound.ITEM_BREAK, 65);
            TextUtil.display(this,"%s has broken!%n", this.getDisplayName());
        }
    }

    @Override
    public boolean receiveEquip(Interactable source) {
        Entity entitySource = (Entity) source;
        entitySource.removeFromInventory(this);
        this.setOwner(entitySource);
        this.onEquip();
        return true;
    }

    @Override
    public boolean receiveTake(Interactable source) {
        Entity entitySource = (Entity) source;
        entitySource.addInventoryItem(this);
        if (this instanceof ItemHoldable && !entitySource.hasHeldItem()) {
            this.receiveEquip(entitySource);
        } else {
            Game.playSound(Sound.ITEM_PICKUP, 65);
            TextUtil.display(this,"%s takes %s. %n", entitySource.getName(), this.getDisplayName());
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
                    AnsiConsole.out.println(useableItem.failMessage());
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

            this.onUnequip();
        }
        sourceEntity.removeEquippedItem(this);
        this.setPos(sourceEntity.getPos());
        Game.playSound(Sound.ITEM_DROP, 65);
        TextUtil.display(this,"%s has dropped %s. %n", sourceEntity.getDisplayName(), this.getDisplayName());
        return super.receiveDrop(source);
    }

    public boolean onEquip() {

        return true;
    }

    public boolean onUnequip() {

        return true;
    }

    public boolean turn() {

        return true;
    }

    public String getDisplayName() {
        return TextUtil.purple(this.getName());
    }
}
