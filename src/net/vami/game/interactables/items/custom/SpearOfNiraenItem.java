package net.vami.game.interactables.items.custom;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.action.Action;
import net.vami.game.interactables.interactions.damagetypes.DamageTypes;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.UseableItem;
import net.vami.util.LogUtil;

import java.util.UUID;

public class SpearOfNiraenItem extends ItemHoldable implements UseableItem {
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
        tempTarget.addInventoryItem(this);
        tempTarget.hurt(
                tempOwner,
                    this.getDamage() + tempOwner.getDamage(),
                    this.getDamageType());

        this.getAttributes().setDamage(this.getDamage() + 1);
    }
}
