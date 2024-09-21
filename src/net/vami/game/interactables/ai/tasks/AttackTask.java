package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.util.TextUtil;
import org.fusesource.jansi.AnsiConsole;

public class AttackTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            TextUtil.display("%s attacks nothing. %n", source.getDisplayName());
        }
        else {
            source.getTarget().receiveAttack(source);
        }
        return true;
    }
}
