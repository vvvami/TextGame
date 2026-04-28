package net.vami.game.interactable.ai.tasks;

import net.vami.game.Game;
import net.vami.game.interactable.entity.Entity;

public class AbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (source.getAbility().isSupport()) {
            Tasks.SUPPORT_ABILITY.taskAction(source);
        } else {
            if (!source.hasTarget()) {
                Game.display(source,"%s tries to cast a spell, but fails. %n", source.getDisplayName());
            } else {
                source.getTarget().receiveAbility(source);
            }
        }
        return true;
    }
}
