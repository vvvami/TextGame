package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class TargetAnyAndAttackTask extends TargetTask {
    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
        }
        new AttackTask().taskAction(source);
        return true;
    }
}
