package net.vami.game.interactables.items.custom;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.damagetypes.SharpDamage;
import net.vami.game.interactables.items.ItemHoldable;
import net.vami.game.interactables.items.UseableItem;

import java.util.UUID;

public class SpearOfNiraenItem extends ItemHoldable implements UseableItem {
    private transient Entity tempOwner;
    public SpearOfNiraenItem(String name, Attributes attributes) {
        super(name, attributes
                .baseDamage(5)
                .damageType(SharpDamage.get));
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
    public void turn() {
        if (this.getOwner() == null) {
            if (tempOwner != null) {
                Game.display(this.tempOwner, "%s returns to the hand of %s. %n",
                        this.getDisplayName(),
                        this.tempOwner.getName());
                this.receiveTake(tempOwner);
            }
        } else {
            super.turn();
        }
    }

    @Override
    public void onUse() {
            Game.display(this.getOwner(), "%s has thrown %s at %s! %n",
                    this.getOwner().getName(),
                    this.getDisplayName(),
                    this.getOwner().getTarget().getName());
            this.getOwner().getTarget().hurt(this.getOwner(), 10f, this.getDamageType());
            tempOwner = this.getOwner();
            this.receiveDrop(this.getOwner());
        }
}
