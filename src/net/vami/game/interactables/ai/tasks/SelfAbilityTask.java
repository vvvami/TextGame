package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class SelfAbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        source.receiveAbility(source);
        return true;
    }
}
