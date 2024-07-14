package net.vami.interactables.ai.tasks;

import net.vami.game.world.Game;
import net.vami.game.world.Node;
import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.items.ItemHoldable;

public class EquipTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
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
