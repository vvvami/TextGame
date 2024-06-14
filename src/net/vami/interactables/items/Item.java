package net.vami.interactables.items;
import net.vami.game.interactions.Action;
import net.vami.game.world.Position;
import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;

public abstract class Item extends Interactable {

    private int durability;
    private Entity owner;

    public Item(String name, int durability) {
        super(name);
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
            kill();
        }
    }

    @Override
    public boolean receiveEquip(Interactable source) {
        Entity entitySource = (Entity) source;
        if (entitySource.equipItem(this)) {
            onEquip(entitySource);
            System.out.printf("%s equips %s. %n", entitySource.getName(), getName());
            return true;
        }
        return false;
    }

    @Override
    public boolean receiveTake(Interactable source) {
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
