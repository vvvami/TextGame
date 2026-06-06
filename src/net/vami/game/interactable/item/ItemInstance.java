package net.vami.game.interactable.item;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.display.Segmental;
import net.vami.game.display.panel.HoverComponent;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.display.sound.Sounds;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;
import net.vami.game.interactable.item.attunement.ItemAttunable;
import net.vami.game.interactable.item.attunement.Attunement;
import net.vami.util.LogUtil;
import net.vami.util.LoggerType;
import net.vami.util.TextUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class ItemInstance extends Interactable {
    private Item item;

    private UUID owner;
    private Attunement attunement;
    private int durability;

    private HashMap<String, Datcon> data = new HashMap<>();

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

    public Datcon getData(String key) {
        return data.get(key);
    }

    public void addData(String key, Object value) {
        if (data.containsKey(key)) {
            LogUtil.log(LoggerType.INFO, "Duplicate data found: %s, %s", key, item);
            return;
        }

        data.put(key, new Datcon<>(value));
    }

    public void removeData(String key) {
        if (!data.containsKey(key)) {
            LogUtil.log(LoggerType.INFO, "Data not found: %s, %s", key, item);
            return;
        }

        data.remove(key);
    }

    public void setData(String key, Object value) {
        if (!data.containsKey(key)) {
            LogUtil.log(LoggerType.INFO, "Data not found: %s, %s", key, item);
            return;
        }

        data.put(key, new Datcon<>(value));
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

        Display.showText(this, "%s has lost %s durability!%n", this, TextUtil.setColor(amount, Color.orange));

        if (durability <= 0) {
            Display.playSound(this.getOwner(), Sounds.ITEM_BREAK, 65);
            getOwner().removeInventoryItem(this);
            getOwner().removeEquippedOrHeldItem(this);
            this.erase();
            Display.showText(this,"%s has broken!%n", this);
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
        if (!(this.get() instanceof ItemAttunable attunable)) {
            Display.showText(this, "%s cannot hold the power of attunement. %n", this);
            return;
        }

        if (!attunable.canAttune()) {
            Display.showText(this,"%s cannot be attuned. %n", this);
            return;
        }

        if (!attunement.applyCondition(this)) {
            Display.showText(this,"%s cannot grasp the reality of \"%s\". %n",
                    this, attunement);
            return;
        }

        this.attunement = attunement;
        this.attunement.onApply(this);
    }

    public void removeAttunement() {
        if (!this.attunement.removeCondition(this)) {
            Display.showText(this,"\"%s\" refuses to leave %s. %n", this, this.attunement.getName());
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

        if (this.get() instanceof ItemHoldable holdable) {
            if (entitySource.hasHeldItem()) {
                entitySource.getHeldItem().onUnequip();
                entitySource.addInventoryItem(entitySource.getHeldItem());
                Display.display(entitySource,
                        new Segmental("%s stashes %s. %n",
                                entitySource, entitySource.getHeldItem()),
                        Sounds.ITEM_PICKUP, 65);

                if (entitySource.getHeldItem() == this) {
                    entitySource.removeEquippedOrHeldItem(this);
                    return true;
                } else {
                    entitySource.removeEquippedOrHeldItem(entitySource.getHeldItem());
                }
            }

            entitySource.removeInventoryItem(this);

            Display.display(entitySource,
                    new Segmental("%s holds %s. %n", entitySource, this),
                    Sounds.ITEM_EQUIP, 65);

            entitySource.setHeldItem(this);
            holdable.onHold(this);
            return super.receiveEquip(source);

        } else if (this.get() instanceof ItemEquipable equipable) {

            if (entitySource.getEquippedItems().size() >= entitySource.getMaxEquipSlots()) {
                Display.showText(this,"%s cannot equip more items. %n",
                        entitySource);
                return false;
            }

            if (entitySource.hasItemEquipped(equipable)) {
                this.onUnequip();
                entitySource.removeEquippedOrHeldItem(this);
                entitySource.addInventoryItem(this);
                Display.playSound(this.getOwner(), Sounds.ITEM_DROP, 65);
                Display.showText(this,"%s stashes %s. %n", entitySource, this);
                return false;
            }

            entitySource.addEquippedItem(this);
            Display.playSound(this.getOwner(), Sounds.ITEM_EQUIP, 65);
            Display.showText(this,"%s equips %s. %n", entitySource, this);
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
        entitySource.addInventoryItem(this);
        if (this.get() instanceof ItemHoldable && !entitySource.hasHeldItem()) {
            this.receiveEquip(entitySource);
        } else {
            Display.display(this.getOwner(),
                    new Segmental("%s takes %s. %n", entitySource, this),
                    Sounds.ITEM_PICKUP, 65);
        }
        this.setOwner(entitySource);
        return true;
    }

    @Override
    public boolean receiveUse(Interactable source) {
        if (this.get() instanceof ItemUseable itemUseable) {
            if (itemUseable.useCondition(this)) {
                itemUseable.onUse(source, this);
                if (this instanceof ItemAttunable
                        && (this.hasAttunement())) {
                    this.getAttunement().onUse(this, (Entity) source);
                }
                return true;

            } else {
                Display.showText(itemUseable.failMessage(this));
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
        Display.display(sourceEntity,
                new Segmental("%s has dropped %s. %n", sourceEntity, this),
                Sounds.ITEM_DROP, 65);
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
        if (this.get() instanceof ItemAttunable
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

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(get().getHoverInfo().getTitle(),
                this.get().getHoverInfo().getDescription() + "\n"
        + (hasAttunement() ? HoverComponent.ATTUNEMENT.get() + getAttunement().getDisplayName() : "")
        + (getMaxDurability() > 0 ? HoverComponent.DURABILITY.get() + getDurability() + "/" + getMaxDurability() : ""));

    }
}
