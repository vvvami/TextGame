package net.vami.interactables.items;
import net.vami.game.*;
import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;

public abstract class Item extends Interactable {

    private int durability;
    private Entity owner;

    public Item(String name, String description, Position position,
                int durability) {
        super(name, description, position);
        this.durability = durability;
        this.addReceivableAction(Action.TAKE);
        this.addReceivableAction(Action.EQUIP);
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
            this.kill();
        }
    }

    @Override
    protected boolean receiveEquip(Interactable source) {
        Entity entitySource = (Entity) source;
        if (entitySource.equipItem(this)) {
            onEquip(entitySource);
            System.out.printf("%s equips %s. %n", entitySource.getName(), Main.ANSI_PURPLE + this.getName() + Main.ANSI_RESET);
            return true;
        }
        return false;
    }

    @Override
    protected boolean receiveTake(Interactable source) {
        Entity entitySource = (Entity) source;
        entitySource.addInventoryItem(this);
        System.out.printf("%s takes %s. %n", entitySource.getName(), this.getName());
        return true;
    }

    public boolean onEquip(Entity owner) {
        return true;
    }

    public boolean turn() {
        return true;
    }

}