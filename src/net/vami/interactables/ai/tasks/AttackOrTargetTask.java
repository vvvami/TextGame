package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class AttackOrTargetTask extends Task {
    @Override
    public void taskAction(Entity source) {
        if (!source.hasTarget()) {
            new TargetTask().taskAction(source);
        }
        else {
            source.getTarget().receiveAttack(source);
        }

    }
}
