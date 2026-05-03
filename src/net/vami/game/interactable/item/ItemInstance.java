package net.vami.game.interactable.item;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.attunement.AttunableItem;
import net.vami.game.interactable.item.attunement.Attunement;

import java.util.UUID;

public class ItemInstance extends Interactable {
    private Item item;

    private UUID owner;
    private Attunement attunement;
    private int durability;

    public ItemInstance(Item item) {
        this.item = item;

        if (this.item instanceof ItemBreakable itemBreakable) {
            this.durability = itemBreakable.maxDurability();
        }

        this.addReceivableAction(Action.TAKE);
        this.addReceivableAction(Action.EQUIP);
        this.addReceivableAction(Action.DROP);
        this.addReceivableAction(Action.USE);
    }

    public Item get() {
        return item;
    }

    public void set(Item item) {
        this.item = item;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public int getMaxDurability() {
        if (item instanceof ItemBreakable breakable) {
            return breakable.maxDurability();
        }
        return 0;
    }

    public void hurt(int amount) {
        durability -= amount;
        if (this.hasAttunement()) {
            this.attunement.onItemHurt(this, amount);
        }

        if (durability <= 0) {
            Game.playSound(this.getOwner(), Sound.ITEM_BREAK, 65);
            getOwner().removeInventoryItem(this);
            getOwner().removeEquippedOrHeldItem(this);
            this.erase();
            Game.display(this,"%s has broken!%n", this.getDisplayName());
        }
    }

    public void setOwner(Entity owner) {
        if (owner == null) {
            this.owner = null;
            return;
        }
        this.owner = owner.getID();
    }

    public Entity getOwner() {

        return (Entity) Interactable.getInteractableFromID(owner);
    }

    public void setAttunement(Attunement attunement) {
        if (!(this instanceof AttunableItem attunable)) {
            Game.display(this, "%s cannot hold the power of attunement. %n", this.getDisplayName());
            return;
        }

        if (!attunable.canAttune()) {
            Game.display(this,"%s cannot be attuned. %n", this.getDisplayName());
            return;
        }

        if (!attunement.applyCondition(this)) {
            Game.display(this,"%s cannot grasp the reality of \"%s\". %n",
                    this.getDisplayName(), attunement.getName());
            return;
        }

        this.attunement = attunement;
        this.attunement.onApply(this);
    }

    public void removeAttunement() {
        if (!this.attunement.removeCondition(this)) {
            Game.display(this,"\"%2$s\" refuses to leave %1$s. %n", this.getDisplayName(), this.attunement.getName());
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

    @Override
    public boolean receiveEquip(Interactable source) {

        if (!(source instanceof Entity entitySource)) {
            return false;
        }

        if (this.get() instanceof ItemHoldable) {
            if (entitySource.hasHeldItem()) {
                entitySource.getHeldItem().onUnequip();
                entitySource.addItem(entitySource.getHeldItem());
                Game.playSound(this.getOwner(), Sound.ITEM_PICKUP, 65);
                Game.display(entitySource,"%s stashes %s. %n", entitySource.getName(), entitySource.getHeldItem().getDisplayName());
            }

            if (entitySource.getHeldItem() == this) {
                entitySource.removeEquippedOrHeldItem(this);
                return true;
            }
            Game.playSound(this.getOwner(), Sound.ITEM_EQUIP, 65);
            Game.display(entitySource,"%s holds %s. %n", entitySource.getName(), this.getDisplayName());
            entitySource.setHeldItem(this);
            return super.receiveEquip(source);

        } else if (this.get() instanceof ItemEquipable equipable) {

            if (entitySource.getEquippedItems().size() >= entitySource.getMaxEquipSlots()) {
                Game.display(this,"%s cannot equip more items. %n",
                        entitySource.getName());
                return false;
            }

            if (entitySource.hasItemEquipped(equipable)) {
                this.onUnequip();
                entitySource.removeEquippedOrHeldItem(this);
                entitySource.addItem(this);
                Game.playSound(this.getOwner(), Sound.ITEM_DROP, 65);
                Game.display(this,"%s stashes %s. %n", entitySource.getName(), this.getDisplayName());
                return false;
            }

            entitySource.addEquippedItem(this);
            Game.playSound(this.getOwner(), Sound.ITEM_EQUIP, 65);
            Game.display(this,"%s equips %s. %n", entitySource.getName(), this.getDisplayName());
            return super.receiveEquip(source);
        }

        entitySource.removeInventoryItem(this);
        this.setOwner(entitySource);
        this.onEquip();
        return true;

    }

    @Override
    public boolean receiveTake(Interactable source) {
        Entity entitySource = (Entity) source;
        entitySource.addItem(this);
        if (this.get() instanceof ItemHoldable && !entitySource.hasHeldItem()) {
            this.receiveEquip(entitySource);
        } else {
            Game.playSound(this.getOwner(), Sound.ITEM_PICKUP, 65);
            Game.display(this.getOwner(),"%s takes %s. %n", entitySource.getName(), this.getDisplayName());
        }
        this.setOwner(entitySource);
        return true;
    }

    @Override
    public boolean receiveUse(Interactable source) {
        if (this.get() instanceof ItemUseable itemUseable) {
            if (itemUseable.useCondition(this)) {
                itemUseable.onUse(source, this);
                if (this instanceof AttunableItem
                        && (this.hasAttunement())) {
                    this.getAttunement().onUse(this, (Entity) source);
                }
                return true;

            } else {
                Game.display(itemUseable.failMessage(this));
            }
        }
        return false;
    }

    @Override
    public boolean receiveDrop(Interactable source) {
        Entity sourceEntity = (Entity) source;
        if ((this.item instanceof ItemHoldable
                && this == sourceEntity.getHeldItem())
                ||
                (this.item instanceof ItemEquipable itemEquipable
                        && sourceEntity.hasItemEquipped(itemEquipable))) {

            this.onUnequip();
        }
        sourceEntity.removeEquippedOrHeldItem(this);
        this.setOwner(null);
        this.setPos(sourceEntity.getPos());
        Game.playSound(sourceEntity, Sound.ITEM_DROP, 65);
        Game.display(sourceEntity,"%s has dropped %s. %n", sourceEntity.getDisplayName(), this.getDisplayName());
        Game.setItemContext(this);
        return super.receiveDrop(source);
    }

    public boolean onEquip() {
        if (this.item instanceof ItemEquipable itemEquipable) {
            itemEquipable.onEquip(this);
        }
        return true;
    }

    public boolean onUnequip() {
        if (this.item instanceof ItemEquipable itemEquipable) {
            itemEquipable.onUnequip(this);
        }
        return true;
    }

    @Override
    public void turn() {
        super.turn();
        this.get().turn(this);
        if (this instanceof AttunableItem
                && this.hasAttunement()) {
            this.attunement.onTurn(this);
        }
    }

    @Override
    public String getName() {
        return this.get().getName();
    }

    @Override
    public String getDisplayName() {
        return this.get().getDisplayName();
    }
}
