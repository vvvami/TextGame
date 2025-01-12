package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;
import net.vami.game.interactables.interactions.Action;
import net.vami.util.TextUtil;

public class AttackTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            TextUtil.display(source,"%s attacks nothing. %n", source.getDisplayName());
        }
        else {
            source.getTarget().receiveAction(source, Action.ATTACK);
        }
        return true;
    }
}
