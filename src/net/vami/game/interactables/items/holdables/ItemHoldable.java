package net.vami.game.interactables.items.holdables;

import net.vami.game.display.sound.Sound;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Modifier;
import net.vami.game.interactables.interactions.ModifierType;
import net.vami.game.interactables.interactions.damagetypes.BluntDamage;
import net.vami.game.interactables.interactions.damagetypes.DamageType;
import net.vami.game.interactables.items.Item;
import org.fusesource.jansi.AnsiConsole;

public abstract class ItemHoldable extends Item {

    private Attributes attributes;

    public ItemHoldable(String name, Attributes attributes) {
        super(name);
        this.attributes = attributes;
        attributes.setDefaults();
    }

    public DamageType getDamageType() {

        return attributes.damageTypeAttribute;
    }

    public float getDamage() {
        float amount = attributes.baseDamageAttribute;

        amount += Modifier.calculate(this.getModifiers(), ModifierType.DAMAGE);

        return amount;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public boolean receiveEquip(Interactable source) {

        if (!(source instanceof Entity entitySource)) {
            return false;
        }

        if (entitySource.hasHeldItem()) {
            entitySource.getHeldItem().onUnequip(entitySource);
            entitySource.addInventoryItem(entitySource.getHeldItem());
            AnsiConsole.out.printf("%s stashes %s. %n", entitySource.getName(), entitySource.getHeldItem().getDisplayName());
        }

        if (entitySource.getHeldItem() == this) {
            entitySource.removeItem(this);
            return true;
        }

        AnsiConsole.out.printf("%s holds %s. %n", entitySource.getName(), this.getDisplayName());
        entitySource.setHeldItem(this);
        return super.receiveEquip(source);
    }

    public static class Attributes {
        int baseDamageAttribute;
        DamageType damageTypeAttribute;

        public Attributes() {
            baseDamageAttribute = -1;
            damageTypeAttribute = null;
        }

        public void setDefaults() {
            if (this.baseDamageAttribute == -1) {this.baseDamageAttribute = 1;}
            if (this.damageTypeAttribute == null) {this.damageTypeAttribute = new BluntDamage();}
        }

        public Attributes baseDamage(int baseDamage) {
            if (baseDamageAttribute == -1) baseDamageAttribute = baseDamage;
            return this;
        }

        public Attributes damageType(DamageType damageType) {
            if (damageTypeAttribute == null) damageTypeAttribute = damageType;
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
