package net.vami.interactables.ai;

import net.vami.interactables.entities.Entity;

public class AbilityTask extends Task {
    @Override
    public void taskAction(Entity source) {
        if (source.hasTarget()) {
            source.getTarget().receiveAbility(source);
        }
    }
}
