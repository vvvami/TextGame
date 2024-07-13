package net.vami.interactables.ai.tasks;

import net.vami.game.world.Game;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.items.Item;

public class SmartEquipTask extends Task {
    @Override
    public void taskAction(Entity source) {
        if (source.hasHeldItem()) {
            source.getBrain().chooseTask(source);
        }
        else {
            new EquipTask().taskAction(source);
        }
    }
}
