package net.vami.interactables.items;

import net.vami.game.DamageType;
import net.vami.game.Position;

public class ItemHoldable extends Item {

    private int baseDamage;
    private DamageType damageType;

    public ItemHoldable(String name, String description, Position position, int durability,
                        int baseDamage, DamageType damageType) {

        super(name, description, position, durability);
        this.baseDamage = baseDamage;
        this.damageType = damageType;
    }

    public void setBaseDamage(int baseDamage) {

        this.baseDamage = baseDamage;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }


    public DamageType getDamageType() {
        return damageType;
    }

    public int getBaseDamage() {
        return baseDamage;
    }


}
