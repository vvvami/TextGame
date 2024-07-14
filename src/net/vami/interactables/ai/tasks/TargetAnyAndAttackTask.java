package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class TargetAnyAndAttackTask extends Task {
    @Override
    public boolean taskAction(Entity source) {
        new TargetTask().taskAction(source);
        new AttackTask().taskAction(source);
        return true;
    }
}
