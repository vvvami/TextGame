package net.vami.interactables.ai;

import net.vami.interactables.entities.Entity;

public class AttackTask extends Task {


    @Override
    public void taskAction(Entity source) {
        if (!source.hasTarget()) {
            if (source.getBrain().hasTask(new TargetTask())) {
                new TargetTask().taskAction(source);
            }
        }
        else {
            source.getTarget().receiveAttack(source);
        }
    }
}
