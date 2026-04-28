package net.vami.game.interactable.item;

import net.vami.game.Game;
import net.vami.game.display.sound.Sound;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageType;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;

public abstract class ItemHoldable extends Item {

    private Attributes attributes;

    public ItemHoldable(String name, Attributes attributes) {
        super(name);
        this.attributes = attributes;
        attributes.setDefaults();
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

    @Override
    public boolean receiveEquip(Interactable source) {

        if (!(source instanceof Entity entitySource)) {
            return false;
        }

        if (entitySource.hasHeldItem()) {
            entitySource.getHeldItem().onUnequip();
            entitySource.addItem(entitySource.getHeldItem());
            Game.playSound(this.getOwner(), Sound.ITEM_PICKUP, 65);
            Game.display(entitySource,"%s stashes %s. %n", entitySource.getName(), entitySource.getHeldItem().getDisplayName());
        }

        if (entitySource.getHeldItem() == this) {
            entitySource.removeEquippedItem(this);
            return true;
        }
        Game.playSound(this.getOwner(), Sound.ITEM_EQUIP, 65);
        Game.display(entitySource,"%s holds %s. %n", entitySource.getName(), this.getDisplayName());
        entitySource.setHeldItem(this);
        return super.receiveEquip(source);
    }

    public static class Attributes {
        float baseDamageAttribute;
        DamageType damageTypeAttribute;

        public Attributes() {
            baseDamageAttribute = -1;
            damageTypeAttribute = null;
        }

        public void setDefaults() {
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
