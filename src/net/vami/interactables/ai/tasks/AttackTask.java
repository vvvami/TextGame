package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public class AttackTask extends Task {

    @Override
    public void taskAction(Entity source) {
        if (!source.hasTarget()) {
            System.out.printf("%s attacks nothing. %n", source.getDisplayName());
        }
        else {
            source.getTarget().receiveAttack(source);
        }
    }
}
