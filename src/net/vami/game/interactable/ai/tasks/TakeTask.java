package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.Interactable;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.item.ItemHoldable;
import net.vami.game.interactable.item.ItemInstance;

public class TakeTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (source.hasHeldItem()) {
            return false;
        }
            for (Interactable interactable : source.getNode().getInteractables()) {
                if (interactable instanceof ItemInstance item) {
                    item.receiveTake(source);
                    source.getBrain().removeTask(this);
                    return true;
                }
            }
            return false;
    }
}
