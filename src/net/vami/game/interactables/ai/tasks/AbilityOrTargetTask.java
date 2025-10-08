package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class AbilityOrTargetTask extends TargetTask {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
            return source.hasTarget();
        } else {
            new AbilityTask().taskAction(source);
        }
        return true;
    }
}
