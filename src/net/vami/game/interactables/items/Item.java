package net.vami.game.interactables.items;
import net.vami.game.display.text.TextFormatter;
import net.vami.game.interactables.interactions.Action;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import org.w3c.dom.Text;

public abstract class Item extends Interactable {

    private int durability;
    private Entity owner;

    public Item(String name, int durability) {
        super(name);
        this.durability = durability;
        this.addReceivableAction(Action.TAKE);
        this.addReceivableAction(Action.EQUIP);
        this.addReceivableAction(Action.DROP);
    }

    public Entity getOwner() {

        return owner;
    }

    public void setOwner(Entity owner) {

        this.owner = owner;
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
            owner.removeItem(this);
            kill();
        }
    }

    @Override
    public boolean receiveEquip(Interactable source) {
        Entity entitySource = (Entity) source;
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

        return true;
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
        this.setPosition(sourceEntity.getPos());
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
