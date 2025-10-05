package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class TargetOrAttackTask extends TargetTask {
    @Override
    public boolean taskAction(Entity source) {
        if (source.hasTarget()) {
            new AttackTask().taskAction(source);
        } else {
            super.taskAction(source);
        }
        return true;
    }
}
