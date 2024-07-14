package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class AbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            System.out.printf("%s tries to cast a spell, but fails. %n", source.getDisplayName());
        } else {
            source.getTarget().receiveAbility(source);
        }
        return true;
    }
}
