package net.vami.game.interactables.ai.tasks;

import net.vami.game.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.holdables.ItemHoldable;

public class TakeTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (source.hasHeldItem()) {
            return false;
        }
            for (Interactable interactable : Game.getInteractables()) {
                if (interactable instanceof ItemHoldable item) {
                    item.receiveTake(source);
                    source.getBrain().removeTask(this);
                    break;
                }
            }
            return true;
    }
}
