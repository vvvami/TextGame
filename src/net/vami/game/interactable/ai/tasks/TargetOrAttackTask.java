package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.entity.Entity;

public class TargetOrAttackTask extends TargetTask {
    @Override
    public boolean taskAction(Entity source) {
        if (source.hasTarget()) {
            Tasks.ATTACK.taskAction(source);
        } else {
            super.taskAction(source);
        }
        return true;
    }
}
