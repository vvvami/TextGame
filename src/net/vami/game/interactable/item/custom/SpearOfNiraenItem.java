package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemUseable;

public class SpearOfNiraenItem extends ItemHoldable implements ItemUseable {
    public SpearOfNiraenItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(2)
                .damageType(DamageTypes.SHARP));
    }

    public SpearOfNiraenItem(Attributes attributes) {
        this("Spear of Niraen", attributes);
    }

    public SpearOfNiraenItem() {
        this(new Attributes());
    }

    @Override
    public boolean useCondition() {
        return (this.getOwner().hasTarget() && !this.getOwner().getTarget().isEnded());
    }

    @Override
    public String failMessage() {
        return String.format("%s has no target.%n", this.getDisplayName());
    }

    @Override
    public void onUse() {
        Entity tempOwner = this.getOwner();
        Entity tempTarget = tempOwner.getTarget();

        Game.display(tempOwner, "%s has thrown %s at %s! %n",
                    tempOwner.getName(),
                    this.getDisplayName(),
                    tempTarget.getName());

        tempOwner.removeInventoryItem(this);
        tempTarget.addItem(this);
        tempTarget.hurt(
                tempOwner,
                    this.getDamage() + tempOwner.getDamage(),
                    this.getDamageType());

        this.getAttributes().setDamage(this.getDamage() + 1);
    }
}
