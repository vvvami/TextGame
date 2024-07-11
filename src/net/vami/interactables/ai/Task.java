package net.vami.interactables.ai;

public class Task {
    int priority;
    TaskType taskType;

    public Task(TaskType taskType, int priority) {
        this.taskType = taskType;
        this.priority = priority;
    }

}
