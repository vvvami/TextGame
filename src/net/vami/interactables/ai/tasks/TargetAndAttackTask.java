package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class TargetAndAttackTask extends TargetTask {

    @Override
    public void taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
        }

        new AttackTask().taskAction(source);
    }
}
