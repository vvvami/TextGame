package net.vami.interactables;
import net.vami.game.*;

import java.util.HashSet;
import java.util.Set;

public class Item extends Interactable {

    private boolean reusable;
    private int damageAmount;
    private DamageType damageType;
    private boolean equipped;

    public Item(String name, String description, Position position,
                boolean reusable, int damageAmount, DamageType damageType) {
        super(name, description, position);
        this.reusable = reusable;
        this.damageAmount = damageAmount;
        this.damageType = damageType;
        this.addReceivableAction(Action.TAKE);
        this.addReceivableAction(Action.EQUIP);
    }

    public void setEquipped(boolean equipped) {

        this.equipped = equipped;
    }

    public void setDamageAmount(int damageAmount) {

        this.damageAmount = damageAmount;
    }

    public void setDamageType(DamageType damageType) {

        this.damageType = damageType;
    }

    public boolean isEquipped() {

        return equipped;
    }

    public DamageType getDamageType() {

        return damageType;
    }

    public int getDamageAmount() {

        return damageAmount;
    }

    public boolean isReusable() {

        return reusable;
    }

    @Override
    protected boolean receiveEquip(Interactable source) {
        if (!(source instanceof Entity)) {
            return false;
        }
        ((Entity) source).setEquippedItem(this);
        return true;
    }
}
