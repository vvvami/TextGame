package net.vami.interactables.ai;

import net.vami.interactables.Interactable;
import net.vami.interactables.entities.Entity;

public abstract class TaskType {
    private float probability;

    public TaskType() {

    }

    public TaskType probability(float probability) {
        this.probability = probability;
        return this;
    }

    public abstract void taskAction(Entity target, Entity source);

}

