package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class IdleTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        return !source.hasTarget();
    }
}
