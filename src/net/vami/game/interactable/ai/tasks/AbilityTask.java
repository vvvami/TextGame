package net.vami.game.interactable.ai.tasks;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.interactable.entity.Entity;

public class AbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (source.getAbility().isSupport()) {
            Tasks.SUPPORT_ABILITY.taskAction(source);
        } else {
            if (!source.hasTarget()) {
                Display.showText(source,"%s tries to cast a spell, but fails. %n", source);
            } else {
                source.getTarget().receiveAbility(source);
            }
        }
        return true;
    }
}
