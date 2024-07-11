package net.vami.interactables.ai;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;

public class AttackTaskType extends TaskType {

    @Override
    public void taskAction(Entity target, Entity source) {
        if (!source.hasTarget()) {

        }
        target.receiveAttack(source);
    }
}
