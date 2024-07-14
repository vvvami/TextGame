package net.vami.interactables.ai.tasks;

import net.vami.game.world.Game;
import net.vami.interactables.entities.Entity;
import net.vami.interactables.items.Item;

public class SmartEquipTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (source.hasHeldItem()) {
            return false;
        }
        else {
            new EquipTask().taskAction(source);
        }
        return true;
    }
}
