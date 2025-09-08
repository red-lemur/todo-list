package com.todolist.controller;

import com.todolist.entity.Task;
import com.todolist.exception.TaskNotFoundException;
import com.todolist.service.TaskService;
import com.todolist.util.TaskTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskControllerTest {
    private final int NUMBER_OF_EXISTING_TASKS = 5;

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskController taskController;

    @BeforeEach
    public void createAndSaveTestTasks() {
        List<Task> createdTasks = TaskTestUtils.createTestTasks(this.NUMBER_OF_EXISTING_TASKS);
        createdTasks.forEach((Task createdTask) -> this.taskService.createTask(createdTask));
    }

    @Test
    public void getAllTasksTest() {
        ResponseEntity<List<Task>> responseEntity = this.taskController.getAllTasks();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Task> tasks = responseEntity.getBody();
        Assertions.assertNotNull(tasks);
        Assertions.assertEquals(this.NUMBER_OF_EXISTING_TASKS, tasks.size());
    }

    @Test
    public void getAllTasksTest_noTaskFound() {
        for (long i = 1L; i <= this.NUMBER_OF_EXISTING_TASKS; i++) {
            this.taskService.deleteTask(i);
        }

        ResponseEntity<List<Task>> responseEntity = this.taskController.getAllTasks();
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        List<Task> tasks = responseEntity.getBody();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.isEmpty());
    }

    @Test
    public void getTask() throws TaskNotFoundException {
        Long taskId = 1L;
        Task task = this.taskController.getTask(taskId);
        Assertions.assertNotNull(task);
        Assertions.assertEquals(taskId, task.getId());
    }

    @Test
    public void getTask_noTaskFound() {
        Long taskId = 6L;
        Assertions.assertThrows(TaskNotFoundException.class, () -> this.taskController.getTask(taskId));
    }

    @Test
    public void getTasksByCompleted() {
        // La classe utilitaire alterne entre false et true pour le booléen completed
        int numberOfCompletedTasks = 2;

        ResponseEntity<List<Task>> responseEntity = this.taskController.getTasksByCompleted(true);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Task> tasks = responseEntity.getBody();
        Assertions.assertNotNull(tasks);

        Assertions.assertEquals(numberOfCompletedTasks, tasks.size());
        for (Task completedTask : tasks) {
            Assertions.assertTrue(completedTask.isCompleted());
        }
    }

    @Test
    public void getTasksByCompleted_noTaskFound() {
        for (long i = 1L; i <= this.NUMBER_OF_EXISTING_TASKS; i++) {
            this.taskService.deleteTask(i);
        }

        ResponseEntity<List<Task>> responseEntity = this.taskController.getTasksByCompleted(true);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        List<Task> tasks = responseEntity.getBody();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.isEmpty());
    }

    @Test
    public void createTask() {
        Task createdTask = new Task("label", "description");

        ResponseEntity<Task> responseEntity = this.taskController.createTask(createdTask);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void updateTask() {
        Long taskId = 2L;
        Task updatedTaskDetails = new Task("updatedLabel", "updatedDescription", true);

        ResponseEntity<Task> responseEntity = this.taskController.updateTask(taskId, updatedTaskDetails);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateTask_noTask() {
        Long taskId = 10L;
        Task updatedTaskDetails = new Task("updatedLabel", "updatedDescription", true);

        ResponseEntity<Task> responseEntity = this.taskController.updateTask(taskId, updatedTaskDetails);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void deleteTask() {
        Long taskId = 3L;

        ResponseEntity<Void> responseEntity = this.taskController.deleteTask(taskId);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
