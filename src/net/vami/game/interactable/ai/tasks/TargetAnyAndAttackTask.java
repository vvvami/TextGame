package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.entity.Entity;

public class TargetAnyAndAttackTask extends TargetTask {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
            if (!source.hasTarget()) {
                return false;
            }
        }

        Tasks.ATTACK.taskAction(source);
        return true;
    }
}
