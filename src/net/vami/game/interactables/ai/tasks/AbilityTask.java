package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;
import org.fusesource.jansi.AnsiConsole;

public class AbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            TextUtil.display("%s tries to cast a spell, but fails. %n", source.getDisplayName());
        } else {
            source.getTarget().receiveAbility(source);
        }
        return true;
    }
}
