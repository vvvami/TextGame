package net.vami.game.interactables.ai.tasks;

import net.vami.game.interactables.entities.Entity;

public class TargetAndAttackTask extends TargetTask {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
            if (!source.hasTarget()) {
                return false;
            }
        }
        new AttackTask().taskAction(source);
        return true;
    }
}
