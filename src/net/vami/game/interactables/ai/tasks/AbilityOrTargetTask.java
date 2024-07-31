package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class AbilityOrTargetTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            new TargetTask().taskAction(source);
        } else {
            source.getTarget().receiveAbility(source);
        }
        return true;
    }
}
