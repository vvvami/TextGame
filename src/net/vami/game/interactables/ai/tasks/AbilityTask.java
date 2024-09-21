package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import org.fusesource.jansi.AnsiConsole;

public class AbilityTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            AnsiConsole.out.printf("%s tries to cast a spell, but fails. %n", source.getDisplayName());
        } else {
            source.getTarget().receiveAbility(source);
        }
        return true;
    }
}
