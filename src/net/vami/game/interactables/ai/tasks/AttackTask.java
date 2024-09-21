package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import org.fusesource.jansi.AnsiConsole;

public class AttackTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            AnsiConsole.out.printf("%s attacks nothing. %n", source.getDisplayName());
        }
        else {
            source.getTarget().receiveAttack(source);
        }
        return true;
    }
}
