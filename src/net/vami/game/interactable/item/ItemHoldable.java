package net.vami.game.interactable.item;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;

public abstract class ItemHoldable extends Item {

    private Attributes attributes;

    public ItemHoldable(String name, Attributes attributes) {
        super(name);
        this.attributes = attributes;
        attributes.initialize();
    }

    public ItemHoldable(Attributes attributes) {
        this(null, attributes);
    }

    public ItemHoldable() {
        this(new Attributes());
    }

    public DamageType getDamageType() {

        return attributes.damageTypeAttribute;
    }

    public float getDamage() {
        return attributes.baseDamageAttribute;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void onHit(Interactable owner, Interactable target, DamageType damageType, float amount) {

    }

    public boolean onHold(ItemInstance item) {
        return true;
    }

    public boolean onStash(ItemInstance item) {
        return true;
    }

    public static class Attributes {
        float baseDamageAttribute;
        DamageType damageTypeAttribute;

        public Attributes() {
            baseDamageAttribute = -1;
            damageTypeAttribute = null;
        }

        public void initialize() {
            if (this.baseDamageAttribute == -1) {this.baseDamageAttribute = 1;}
            if (this.damageTypeAttribute == null) {this.damageTypeAttribute = DamageTypes.BLUNT;}
        }

        public Attributes baseDamage(float baseDamage) {
            if (baseDamageAttribute == -1) baseDamageAttribute = baseDamage;
            return this;
        }

        public Attributes damageType(DamageType damageType) {
            if (damageTypeAttribute == null) damageTypeAttribute = damageType;
            return this;
        }

        public Attributes setDamage(float damage) {
            baseDamageAttribute = damage;
            return this;
        }

        public Attributes setDamageType(DamageType damageType) {
            damageTypeAttribute = damageType;
            return this;
        }

        public Attributes copyOf(ItemHoldable itemHoldable) {
            Attributes attributes = itemHoldable.attributes;
            this.damageTypeAttribute = attributes.damageTypeAttribute;
            this.baseDamageAttribute = attributes.baseDamageAttribute;
            return this;
        }
    }

}
