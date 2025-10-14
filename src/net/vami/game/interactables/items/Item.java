package net.vami.game.interactables.items;
import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.items.attunement.AttunableItem;
import net.vami.game.interactables.items.attunement.Attunement;
import net.vami.util.TextUtil;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;

import java.awt.*;
import java.util.UUID;

public class Item extends Interactable {

    private int durability;
    private UUID owner;
    private Attunement attunement;

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

    public Item() {
        this(null);
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

    public void setAttunement(Attunement attunement) {
        if (!(this instanceof AttunableItem attunable)) {
            TextUtil.display(this, "%s cannot hold the power of attunement. %n", this.getDisplayName());
            return;
        }

        if (!attunable.canAttune()) {
            TextUtil.display(this,"%s cannot be attuned. %n", this.getDisplayName());
            return;
        }

        if (!attunement.applyCondition(this)) {
            TextUtil.display(this,"%s cannot grasp the reality of \"%s\". %n",
                    this.getDisplayName(), attunement.getName());
            return;
        }

        this.attunement = attunement;
        this.attunement.onApply(this);
    }

    public void removeAttunement() {
        if (!this.attunement.removeCondition(this)) {
            TextUtil.display(this,"\"%2$s\" refuses to leave %1$s. %n", this.getDisplayName(), this.attunement.getName());
        }
        this.attunement.onRemove(this);
        this.attunement = null;
    }

    public Attunement getAttunement() {
        return this.attunement;
    }

    public boolean hasAttunement() {
        return attunement != null;
    }


    public void hurt(int amount) {
        durability -= amount;
        if (this.hasAttunement()) {
            this.attunement.onItemHurt(this, amount);
        }

        if (durability <= 0) {
            Game.playSound(this.getOwner(), Sound.ITEM_BREAK, 65);
            getOwner().removeFromInventory(this);
            getOwner().removeEquippedItem(this);
            erase();
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
            Game.playSound(this.getOwner(), Sound.ITEM_PICKUP, 65);
            TextUtil.display(this.getOwner(),"%s takes %s. %n", entitySource.getName(), this.getDisplayName());
        }
        this.setOwner(entitySource);
        return true;
    }

    @Override
    public boolean receiveUse(Interactable source) {
        if (this instanceof UseableItem useableItem) {
                if (useableItem.useCondition()) {
                    useableItem.onUse();
                    if (this instanceof AttunableItem
                    && (this.hasAttunement())) {
                        this.getAttunement().onUse(this, (Entity) source);
                    }
                    return true;

                } else {
                    TextUtil.display(useableItem.failMessage());
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
        Game.playSound(this.getOwner(), Sound.ITEM_DROP, 65);
        TextUtil.display(this.getOwner(),"%s has dropped %s. %n", sourceEntity.getDisplayName(), this.getDisplayName());
        return super.receiveDrop(source);
    }

    public boolean onEquip() {

        return true;
    }

    public boolean onUnequip() {

        return true;
    }

    @Override
    public void turn() {
        super.turn();
        if (this instanceof AttunableItem attunable
        && this.hasAttunement()) {
            this.attunement.onTurn(this);
        }
    }

    public String getDisplayName() {
        return TextUtil.setColor(this.getName(), Color.magenta);
    }
}
