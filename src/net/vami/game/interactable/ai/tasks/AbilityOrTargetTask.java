package net.vami.game.interactable.ai.tasks;

import net.vami.game.interactable.entity.Entity;

public class AbilityOrTargetTask extends TargetTask {

    @Override
    public boolean taskAction(Entity source) {
        if (!source.hasTarget()) {
            super.taskAction(source);
            return source.hasTarget();
        } else {
            Tasks.ABILITY.taskAction(source);
        }
        return true;
    }
}
