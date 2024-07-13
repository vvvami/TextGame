package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class TargetOrAttackTask extends Task {
    @Override
    public void taskAction(Entity source) {
        if (source.hasTarget()) {
            new AttackTask().taskAction(source);
        } else {
            new TargetTask().taskAction(source);
        }
    }
}
