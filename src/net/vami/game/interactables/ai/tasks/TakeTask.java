package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.ItemHoldable;

public class TakeTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (source.hasHeldItem()) {
            return false;
        }
            for (Interactable interactable : source.getNode().getInteractables()) {
                if (interactable instanceof ItemHoldable item) {
                    item.receiveTake(source);
                    source.getBrain().removeTask(this);
                    break;
                }
            }
            return true;
    }
}
