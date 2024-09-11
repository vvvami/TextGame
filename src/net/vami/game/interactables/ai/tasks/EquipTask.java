package net.vami.game.interactables.ai.tasks;

import net.vami.game.world.Game;
import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.holdables.ItemHoldable;

public class EquipTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (source.hasHeldItem()) {
            return false;
        }
            for (Interactable interactable : Game.getInteractables()) {
                if (interactable instanceof ItemHoldable item) {
                    item.receiveEquip(source);
                    source.getBrain().removeTask(this);
                    break;
                }
            }
            return true;
    }
}
