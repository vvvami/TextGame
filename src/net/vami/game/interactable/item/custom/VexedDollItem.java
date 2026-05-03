package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
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
    public void onUse(Interactable source) {
        Entity sourceEntity = (Entity) source;
        sourceEntity.getTarget().removeStatus(status);

        Game.display("%s had their %s removed!", sourceEntity.getTarget().getName(), status.getName());
    }

    @Override
    public String failMessage() {
        return "The doll does nothing.";
    }
}
