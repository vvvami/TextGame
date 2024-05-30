package net.vami.interactables.items;

import net.vami.game.DamageType;
import net.vami.game.Position;

public class ItemHoldable extends Item {

    private int damageAmount;
    private DamageType damageType;

    public ItemHoldable(String name, String description, Position position, int durability,
                        int damageAmount, DamageType damageType) {

        super(name, description, position, durability);
        this.damageAmount = damageAmount;
        this.damageType = damageType;
    }

    public void setDamageAmount(int damageAmount) {

        this.damageAmount = damageAmount;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }


    public DamageType getDamageType() {
        return damageType;
    }

    public int getDamageAmount() {
        return damageAmount;
    }


}
