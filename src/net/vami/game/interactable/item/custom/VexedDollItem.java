package net.vami.game.interactable.item.custom;

import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemUseable;

public class VexedDollItem extends Item implements ItemUseable {
    private transient Status status;

    @Override
    public boolean useCondition() {
        if (this.getOwner().getTarget() == null) {return false;}

        for (Status.Instance instance : this.getOwner().getTarget().getStatuses()) {
            if (!instance.getStatus().isHarmful()) {
                status = instance.getStatus();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUse() {
        this.getOwner().getTarget().removeStatus(status);
    }

    @Override
    public String failMessage() {
        return "The doll does nothing.";
    }
}
