package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.entity.Entity;

public class AttackOrTargetTask extends TargetTask {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
        }
        else {
            source.getTarget().receiveAttack(source);
        }
        return true;
    }
}
