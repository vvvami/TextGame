package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.entity.Entity;

public class SelfAbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        source.receiveAbility(source);
        return true;
    }
}
