package net.vami.interactables.ai.tasks;

import net.vami.interactables.entities.Entity;

public abstract class Task {
    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract boolean taskAction(Entity source);

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }
}

