package com.todolist.util;

import com.todolist.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskTestUtils {
    public static List<Task> createTestTasks(int numberOfTasksToCreate) {
        List<Task> createdTasks = new ArrayList<>();
        for (int i = 1; i <= numberOfTasksToCreate; i++) {
            String taskLabel = "label" + i;
            String taskDescription = "description" + i;
            boolean taskCompleted = i % 2 == 0;
            Task newTask = new Task(taskLabel, taskDescription, taskCompleted);
            createdTasks.add(newTask);
        }
        return createdTasks;
    }
}
