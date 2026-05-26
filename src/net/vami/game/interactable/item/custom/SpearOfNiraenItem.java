package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.display.panel.HoverInfo;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.damagetypes.DamageTypes;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;
import net.vami.game.interactable.item.attunement.ItemAttunable;

public class SpearOfNiraenItem extends ItemHoldable implements ItemUseable, ItemAttunable {
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
    public boolean useCondition(ItemInstance item) {
        return (item.getOwner().hasTarget() && !item.getOwner().getTarget().isEnded());
    }

    @Override
    public String failMessage(ItemInstance item) {
        return "There is nothing to target. %n";
    }

    @Override
    public void onUse(Interactable source, ItemInstance item) {
        Entity sourceEntity = (Entity) source;
        Entity tempTarget = sourceEntity.getTarget();

        Game.display(sourceEntity, "%s has thrown %s at %s! %n",
                    sourceEntity, this, tempTarget);

        sourceEntity.removeEquippedOrHeldItem(item);
        tempTarget.addInventoryItem(item);
        tempTarget.hurt(
                sourceEntity,
                this.getDamage() + sourceEntity.getDamage(),
                this.getDamageType());

        this.getAttributes().setDamage(this.getDamage() + 1);
    }

    @Override
    public HoverInfo getHoverInfo() {
        return new HoverInfo(getDisplayName(),"A holy spear of light.");
    }
}
