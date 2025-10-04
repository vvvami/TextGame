package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.Interactable;
import net.vami.game.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class AttackOrTargetTask extends TargetTask {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            new TargetTask().taskAction(source);
        }
        else {
            source.getTarget().receiveAttack(source);
        }
        return true;
    }
}
