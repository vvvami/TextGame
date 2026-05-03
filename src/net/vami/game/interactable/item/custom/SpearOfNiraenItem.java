package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
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
    public void onUse(Interactable source) {
        Entity sourceEntity = (Entity) source;
        Entity tempTarget = sourceEntity.getTarget();

        Game.display(sourceEntity, "%s has thrown %s at %s! %n",
                    sourceEntity.getName(),
                    this.getDisplayName(),
                    tempTarget.getName());

        sourceEntity.removeInventoryItem(this);
        tempTarget.addItem(this);
        tempTarget.hurt(
                sourceEntity,
                    this.getDamage() + sourceEntity.getDamage(),
                    this.getDamageType());

        this.getAttributes().setDamage(this.getDamage() + 1);
    }
}
