package net.vami.game.interactable.item.custom;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.statuses.Status;
import net.vami.game.interactable.interaction.statuses.StatusInstance;
import net.vami.game.interactable.item.Item;
import net.vami.game.interactable.item.ItemInstance;
import net.vami.game.interactable.item.ItemUseable;

public class VexedDollItem extends Item implements ItemUseable {
    private transient Status status;

    public VexedDollItem() {
        super("Vexed Doll");
    }

    @Override
    public boolean useCondition(ItemInstance item) {
        if (item.getOwner().getTarget() == null) {return false;}

        for (StatusInstance instance : item.getOwner().getTarget().getStatuses()) {
            if (!instance.getStatus().isHarmful()) {
                status = instance.getStatus();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUse(Interactable source, ItemInstance instance) {
        Entity sourceEntity = (Entity) source;
        sourceEntity.getTarget().removeStatus(status);

        Display.showText("%s had their %s removed!", sourceEntity.getTarget(), status);
    }

    @Override
    public String failMessage(ItemInstance item) {
        return "The doll does nothing.";
    }
}
