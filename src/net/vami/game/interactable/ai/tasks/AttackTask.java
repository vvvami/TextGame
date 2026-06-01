package net.vami.game.interactable.ai.tasks;

import net.vami.game.Game;
import net.vami.game.display.Display;
import net.vami.game.interactable.entity.Entity;
import net.vami.game.interactable.interaction.action.Action;

public class AttackTask extends Task {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            Display.showText(source,"%s attacks nothing. %n", source);
        }
        else {
            source.getTarget().receiveAction(source, Action.ATTACK);
        }
        return true;
    }
}
