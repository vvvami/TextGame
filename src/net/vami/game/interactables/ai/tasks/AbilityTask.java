package net.vami.game.interactables.ai.tasks;

import net.vami.game.Game;
import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;

public class AbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (source.getAbility().isSupport()) {
            new SupportAbilityTask().taskAction(source);
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
