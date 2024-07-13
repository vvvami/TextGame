package net.vami.interactables.ai;

import net.vami.interactables.entities.Entity;

public abstract class Task {
    private int priority;

    public Task() {
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract void taskAction(Entity source);

}

