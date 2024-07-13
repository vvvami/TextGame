package net.vami.interactables.ai;

import net.vami.game.display.TextFormatter;
import net.vami.interactables.ai.tasks.Task;
import net.vami.interactables.entities.Entity;

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
            return (task1.equals(task2));
        }
        return false;
    }

    public void chooseTask(Entity source) {
        quickSort(taskList, 0, taskList.size() - 1);

        float priorityAvg = 0;
        for (Task task : taskList) {
            priorityAvg += task.getPriority();
        }
        priorityAvg = priorityAvg / taskList.size();

        int rnd = new Random().nextInt((int) priorityAvg + 1);
        for (Task task : taskList) {

            if (rnd <= task.getPriority()) {
                task.taskAction(source);
                return;
            }
        }

        taskList.getLast().taskAction(source);
    }

    public void doTask(Entity source, Task task) {
        task.taskAction(source);
    }

    private void quickSort(List<Task> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(List<Task> arr, int begin, int end) {
        int pivot = arr.get(end).getPriority();
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).getPriority() <= pivot) {
                i++;

                Task swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        Task swapTemp = arr.get(i + 1);
        arr.set(i + 1, arr.get(end));
        arr.set(end, swapTemp);

        return i + 1;
    }
}
