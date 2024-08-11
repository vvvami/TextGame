package net.vami.game.interactables.ai;

import net.vami.game.interactables.ai.tasks.Task;
import net.vami.game.interactables.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Brain {
    private List<Task> taskList = new ArrayList<>();

    public void addTask(Task task, int priority) {
        if (priority < 1) {
            throw new RuntimeException("Task priority cannot be lower than 1!");
        }
        task.setPriority(priority);
        taskList.add(task);
    }

    public void removeTask(Task task) {
        Task temp = null;
        for (Task taskIndex : taskList) {
            if (task.equals(taskIndex)) {
                temp = taskIndex;
            }
        }

        if (temp != null) {
            taskList.remove(temp);
        }
    }

    public Task getTask(Task task1) {
        for (Task task2 : taskList) {
            if (task1.getClass() == task2.getClass()) {
                return task2;
            }
        }
        return null;
    }

    public boolean hasTask(Task task1) {
        for (Task task2 : taskList) {
            if (task1.equals(task2)) {
                return true;
            }
        }
        return false;
    }

    public void selectTask(Entity source) {
        List<Task> tempList = new ArrayList<>(taskList);

        while (!tempList.isEmpty()) {
            Task chosenTask = chooseTask();
            if (chosenTask.taskAction(source)) {
                break;
            }

            tempList.remove(chosenTask);
        }
    }

    Task chooseTask() {
        List<Integer> priorityList = taskList.stream().map(Task::getPriority).toList();
        int totalPriority = priorityList.stream().reduce(0, Integer::sum);

        int rnd = new Random().nextInt(totalPriority);
        int acc = 0;

        for (int i = 0; i < priorityList.size(); i++) {
            acc += priorityList.get(i);
            if (rnd < acc) {
                return taskList.get(i);
            }
        }
        // This should never reach here
        return null;
    }

    public void doTask(Entity source, Task task) {
        task.taskAction(source);
    }
}
