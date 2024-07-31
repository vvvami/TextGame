package net.vami.game.interactables.ai.tasks;

import net.vami.game.world.Game;
import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.items.Item;

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
