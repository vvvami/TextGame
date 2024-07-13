package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class SelfAbilityTask extends Task {
    @Override
    public void taskAction(Entity source) {
        source.receiveAbility(source);
    }
}
