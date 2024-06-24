package net.vami.interactables.items;

import net.vami.interactables.interactions.DamageType;

public abstract class ItemHoldable extends Item {

    private int baseDamage;
    private DamageType damageType;
    private Attributes attributes;

    public ItemHoldable(String name, Attributes attributes) {
        super(name, attributes.durabilityAttribute);
        this.attributes = attributes;
        attributes.setDefaults();

        baseDamage = attributes.baseDamageAttribute;
        damageType = attributes.damageTypeAttribute;
        this.setDurability(attributes.durabilityAttribute);
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

    public static class Attributes {
        int baseDamageAttribute;
        DamageType damageTypeAttribute;
        int durabilityAttribute;

        public Attributes() {
            baseDamageAttribute = -1;
            damageTypeAttribute = DamageType.NONE;
            durabilityAttribute = -1;
        }

        public void setDefaults() {
            if (this.baseDamageAttribute == -1) {this.baseDamageAttribute = 1;}
            if (this.damageTypeAttribute == DamageType.NONE) {this.damageTypeAttribute = DamageType.BLUNT;}
            if (this.durabilityAttribute == -1) {this.durabilityAttribute = 1;}
        }

        public Attributes baseDamage(int baseDamage) {
            baseDamageAttribute = baseDamage;
            return this;
        }

        public Attributes damageType(DamageType damageType) {
            damageTypeAttribute = damageType;
            return this;
        }

        public Attributes durability(int durability) {
            durabilityAttribute = durability;
            return this;
        }

        public Attributes copyOf(ItemHoldable itemHoldable) {
            Attributes attributes = itemHoldable.attributes;
            this.durabilityAttribute = attributes.durabilityAttribute;
            this.damageTypeAttribute = attributes.damageTypeAttribute;
            this.baseDamageAttribute = attributes.baseDamageAttribute;
            return this;
        }
    }

}
