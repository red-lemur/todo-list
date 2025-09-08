package com.todolist.service;

import com.todolist.entity.Task;
import com.todolist.exception.TaskNotFoundException;
import com.todolist.repository.TaskRepository;
import com.todolist.util.TaskTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskServiceTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    public void getAllTasks_noTask() {
        List<Task> actualTasks = this.taskService.getAllTasks();
        Assertions.assertTrue(actualTasks.isEmpty());
    }

    @Test
    public void getAllTasks_oneTask() {
        int expectedTasksNb = 1;
        List<Task> expectedTasks = this.createAndSaveTestTasks(expectedTasksNb);
        List<Task> actualTasks = this.taskService.getAllTasks();
        Assertions.assertEquals(expectedTasksNb, actualTasks.size());

        Task expectedTask = expectedTasks.getFirst();
        Task actualTask = actualTasks.getFirst();
        this.assertTasksAreTheSame(expectedTask, actualTask);
    }

    @Test
    public void getAllTasks_threeTasks() {
        int expectedTasksNb = 3;
        List<Task> expectedTasks = this.createAndSaveTestTasks(expectedTasksNb);
        List<Task> actualTasks = this.taskService.getAllTasks();
        Assertions.assertEquals(expectedTasksNb, actualTasks.size());

        for (int i = 0; i < expectedTasksNb; i++) {
            Task expectedTask = expectedTasks.get(i);
            Task actualTask = actualTasks.get(i);
            this.assertTasksAreTheSame(expectedTask, actualTask);
        }
    }

    @Test
    public void getTask_noTask() {
        Assertions.assertThrows(TaskNotFoundException.class, () -> this.taskService.getTask(1L));
    }

    @Test
    public void getTask_oneTask() throws TaskNotFoundException {
        int expectedTasksNb = 1;
        List<Task> expectedTasks = this.createAndSaveTestTasks(expectedTasksNb);
        Task expectedTask = expectedTasks.getFirst();

        Assertions.assertThrows(TaskNotFoundException.class, () -> this.taskService.getTask(0L));

        Assertions.assertThrows(TaskNotFoundException.class, () -> this.taskService.getTask(-1L));

        Assertions.assertThrows(TaskNotFoundException.class, () -> this.taskService.getTask(2L));

        Task actualTask = this.taskService.getTask(1L);
        Assertions.assertNotNull(actualTask);
        this.assertTasksAreTheSame(expectedTask, actualTask);
    }

    @Test
    public void getTask_threeTasks() throws TaskNotFoundException {
        int expectedTasksNb = 3;
        List<Task> expectedTasks = this.createAndSaveTestTasks(expectedTasksNb);

        Task expectedTask = expectedTasks.get(1);

        Task actualTask = this.taskService.getTask(1L);
        Assertions.assertNotNull(actualTask);
        this.assertTasksAreDifferent(expectedTask, actualTask);

        actualTask = this.taskService.getTask(2L);
        Assertions.assertNotNull(actualTask);
        this.assertTasksAreTheSame(expectedTask, actualTask);

        actualTask = this.taskService.getTask(3L);
        Assertions.assertNotNull(actualTask);
        this.assertTasksAreDifferent(expectedTask, actualTask);

        Assertions.assertThrows(TaskNotFoundException.class, () -> this.taskService.getTask(4L));
    }

    @Test
    public void getTasksByCompleted_noTask() {
        List<Task> actualTasks = this.taskService.getTasksByCompleted(true);
        Assertions.assertTrue(actualTasks.isEmpty());
        actualTasks = this.taskService.getTasksByCompleted(false);
        Assertions.assertTrue(actualTasks.isEmpty());
    }

    @Test
    public void getTasksByCompleted_twoTasksCompleted_noTaskNotCompleted() {
        int expectedCompletedTasksNb = 2;
        for (int i = 0; i < expectedCompletedTasksNb; i++) {
            this.taskRepository.save(new Task("", "", true));
        }

        List<Task> actualTasks = this.taskService.getTasksByCompleted(true);
        Assertions.assertEquals(expectedCompletedTasksNb, actualTasks.size());
        for (Task actualTask : actualTasks) {
            Assertions.assertTrue(actualTask.isCompleted());
        }

        actualTasks = this.taskService.getTasksByCompleted(false);
        Assertions.assertTrue(actualTasks.isEmpty());
    }

    @Test
    public void getTasksByCompleted_noTaskCompleted_oneTaskNotCompleted() {
        int expectedNotCompletedTasksNb = 1;
        for (int i = 0; i < expectedNotCompletedTasksNb; i++) {
            this.taskRepository.save(new Task("", ""));
        }

        List<Task> actualTasks = this.taskService.getTasksByCompleted(true);
        Assertions.assertTrue(actualTasks.isEmpty());

        actualTasks = this.taskService.getTasksByCompleted(false);
        Assertions.assertEquals(expectedNotCompletedTasksNb, actualTasks.size());
        for (Task actualTask : actualTasks) {
            Assertions.assertFalse(actualTask.isCompleted());
        }
    }

    @Test
    public void getTasksByCompleted_threeTaskCompleted_twoTasksNotCompleted() {
        int expectedCompletedTasksNb = 3;
        for (int i = 0; i < expectedCompletedTasksNb; i++) {
            this.taskRepository.save(new Task("", "", true));
        }
        int expectedNotCompletedTasksNb = 2;
        for (int i = 0; i < expectedNotCompletedTasksNb; i++) {
            this.taskRepository.save(new Task("", ""));
        }

        List<Task> actualTasks = this.taskService.getTasksByCompleted(true);
        Assertions.assertEquals(expectedCompletedTasksNb, actualTasks.size());
        for (Task actualTask : actualTasks) {
            Assertions.assertTrue(actualTask.isCompleted());
        }

        actualTasks = this.taskService.getTasksByCompleted(false);
        Assertions.assertEquals(expectedNotCompletedTasksNb, actualTasks.size());
        for (Task actualTask : actualTasks) {
            Assertions.assertFalse(actualTask.isCompleted());
        }
    }

    @Test
    public void createTask_oneTask() {
        int expectedTasksNb = 1;
        Task expectedTask = new Task("label", "description");
        this.taskService.createTask(expectedTask);

        List<Task> actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(expectedTasksNb, actualTasks.size());
        Task actualTask = actualTasks.getFirst();
        this.assertTasksAreTheSame(expectedTask, actualTask);
    }

    @Test
    public void createTask_oneTask_forceId() {
        int expectedTasksNb = 1;
        Task expectedTask = new Task("label", "description");
        expectedTask.setId(3L);
        this.taskService.createTask(expectedTask);

        List<Task> actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(expectedTasksNb, actualTasks.size());
        Task actualTask = actualTasks.getFirst();
        this.assertTasksAreTheSame(expectedTask, actualTask);
    }

    @Test
    public void createTask_threeTask() {
        int expectedTasksNb = 3;
        Task expectedTask1 = new Task("label1", "description1", true);
        this.taskService.createTask(expectedTask1);
        Task expectedTask2 = new Task("label2", "description2");
        this.taskService.createTask(expectedTask2);
        Task expectedTask3 = new Task("label3", "description3");
        this.taskService.createTask(expectedTask3);

        List<Task> actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(expectedTasksNb, actualTasks.size());

        Task actualTask1 = actualTasks.getFirst();
        this.assertTasksAreTheSame(expectedTask1, actualTask1);
        this.assertTasksAreDifferent(expectedTask2, actualTask1);
        this.assertTasksAreDifferent(expectedTask3, actualTask1);

        Task actualTask2 = actualTasks.get(1);
        this.assertTasksAreDifferent(expectedTask1, actualTask2);
        this.assertTasksAreTheSame(expectedTask2, actualTask2);
        this.assertTasksAreDifferent(expectedTask3, actualTask2);

        Task actualTask3 = actualTasks.get(2);
        this.assertTasksAreDifferent(expectedTask1, actualTask3);
        this.assertTasksAreDifferent(expectedTask2, actualTask3);
        this.assertTasksAreTheSame(expectedTask3, actualTask3);
    }

    @Test
    public void updateTask_oneTask() {
        int existingTasksNb = 1;
        List<Task> existingTasks = this.createAndSaveTestTasks(existingTasksNb);
        Task existingTask = existingTasks.getFirst();

        Task updatedTaskDetails = new Task("updatedLabel", "updatedDescription", false);
        updatedTaskDetails.setId(5L); // On essaie de forcer l'ID
        Long updatedTaskId = 1L;
        this.taskService.updateTask(updatedTaskId, updatedTaskDetails);

        List<Task> allTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, allTasks.size());

        Task actualUpdatedTask = this.taskRepository.findById(updatedTaskId).orElse(null);
        Assertions.assertNotNull(actualUpdatedTask);
        Assertions.assertEquals(existingTask.getId(), actualUpdatedTask.getId());
        Assertions.assertEquals(updatedTaskDetails.getLabel(), actualUpdatedTask.getLabel());
        Assertions.assertEquals(updatedTaskDetails.getDescription(), actualUpdatedTask.getDescription());
        Assertions.assertEquals(updatedTaskDetails.isCompleted(), actualUpdatedTask.isCompleted());
    }

    @Test
    public void updateTask_twoTasks_oneTaskUpdated() {
        int existingTasksNb = 2;
        List<Task> existingTasks = this.createAndSaveTestTasks(existingTasksNb);

        Task updatedTaskDetails = new Task("updatedLabel", "updatedDescription", false);
        updatedTaskDetails.setId(0L); // On essaie de forcer l'ID
        Long updatedTaskId = 2L;
        this.taskService.updateTask(updatedTaskId, updatedTaskDetails);

        List<Task> allTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, allTasks.size());

        for (Task actualTask : allTasks) {
            Assertions.assertNotNull(actualTask);

            Task existingTask = existingTasks.stream().filter((Task task) -> task.getId().equals(actualTask.getId()))
                    .findAny().orElse(null);
            Assertions.assertNotNull(existingTask);

            if (updatedTaskId.equals(actualTask.getId())) {
                Assertions.assertEquals(updatedTaskDetails.getLabel(), actualTask.getLabel());
                Assertions.assertEquals(updatedTaskDetails.getDescription(), actualTask.getDescription());
                Assertions.assertEquals(updatedTaskDetails.isCompleted(), actualTask.isCompleted());
            } else {
                Assertions.assertEquals(existingTask.getLabel(), actualTask.getLabel());
                Assertions.assertEquals(existingTask.getDescription(), actualTask.getDescription());
                Assertions.assertEquals(existingTask.isCompleted(), actualTask.isCompleted());
            }
        }
    }

    @Test
    public void updateTask_threeTasks_twoTasksUpdated() {
        int existingTasksNb = 3;
        List<Task> existingTasks = this.createAndSaveTestTasks(existingTasksNb);

        Task firstUpdatedTaskDetails = new Task("updatedLabel1", "updatedDescription1", true);
        firstUpdatedTaskDetails.setId(-3L); // On essaie de forcer l'ID
        Long firstUpdatedTaskId = 1L;
        this.taskService.updateTask(firstUpdatedTaskId, firstUpdatedTaskDetails);

        Task secondUpdatedTaskDetails = new Task("updatedLabel2", "updatedDescription2", false);
        secondUpdatedTaskDetails.setId(5L); // On essaie de forcer l'ID
        Long secondUpdatedTaskId = 3L;
        this.taskService.updateTask(secondUpdatedTaskId, secondUpdatedTaskDetails);

        List<Task> allTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, allTasks.size());

        for (Task actualTask : allTasks) {
            Assertions.assertNotNull(actualTask);

            Task existingTask = existingTasks.stream().filter((Task task) -> task.getId().equals(actualTask.getId()))
                    .findAny().orElse(null);
            Assertions.assertNotNull(existingTask);

            if (firstUpdatedTaskId.equals(actualTask.getId())) {
                Assertions.assertEquals(firstUpdatedTaskDetails.getLabel(), actualTask.getLabel());
                Assertions.assertEquals(firstUpdatedTaskDetails.getDescription(), actualTask.getDescription());
                Assertions.assertEquals(firstUpdatedTaskDetails.isCompleted(), actualTask.isCompleted());
            } else if (secondUpdatedTaskId.equals(actualTask.getId())) {
                Assertions.assertEquals(secondUpdatedTaskDetails.getLabel(), actualTask.getLabel());
                Assertions.assertEquals(secondUpdatedTaskDetails.getDescription(), actualTask.getDescription());
                Assertions.assertEquals(secondUpdatedTaskDetails.isCompleted(), actualTask.isCompleted());
            } else {
                Assertions.assertEquals(existingTask.getLabel(), actualTask.getLabel());
                Assertions.assertEquals(existingTask.getDescription(), actualTask.getDescription());
                Assertions.assertEquals(existingTask.isCompleted(), actualTask.isCompleted());
            }
        }
    }

    @Test
    public void deleteTask_noTask() {
        List<Task> existingTasks = this.taskRepository.findAll();
        Assertions.assertTrue(existingTasks.isEmpty());

        this.taskService.deleteTask(-9L);

        List<Task> actualTasks = this.taskRepository.findAll();
        Assertions.assertTrue(actualTasks.isEmpty());

        this.taskService.deleteTask(0L);

        actualTasks = this.taskRepository.findAll();
        Assertions.assertTrue(actualTasks.isEmpty());

        this.taskService.deleteTask(1L);

        actualTasks = this.taskRepository.findAll();
        Assertions.assertTrue(actualTasks.isEmpty());
    }

    @Test
    public void deleteTask_oneTask_noTaskLeft() {
        int existingTasksNb = 1;
        this.createAndSaveTestTasks(existingTasksNb);
        List<Task> existingTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, existingTasks.size());

        this.taskService.deleteTask(-9L);

        List<Task> actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, actualTasks.size());

        this.taskService.deleteTask(0L);

        actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, actualTasks.size());

        this.taskService.deleteTask(3L);

        actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, actualTasks.size());

        this.taskService.deleteTask(1L);

        actualTasks = this.taskRepository.findAll();
        Assertions.assertTrue(actualTasks.isEmpty());
    }

    @Test
    public void deleteTask_threeTasks_oneTaskLeft() {
        int existingTasksNb = 3;
        int expectedRemainingTasksNb = 1;
        Long expectedRemainingTaskId = 2L;
        this.createAndSaveTestTasks(existingTasksNb);
        List<Task> existingTasks = this.taskRepository.findAll();
        Assertions.assertEquals(existingTasksNb, existingTasks.size());

        this.taskService.deleteTask(3L);
        this.taskService.deleteTask(1L);

        List<Task> actualTasks = this.taskRepository.findAll();
        Assertions.assertEquals(expectedRemainingTasksNb, actualTasks.size());
        Assertions.assertEquals(expectedRemainingTaskId, actualTasks.getFirst().getId());
    }

    private List<Task> createAndSaveTestTasks(int numberOfTasksToCreate) {
        List<Task> createdTasks = TaskTestUtils.createTestTasks(numberOfTasksToCreate);
        createdTasks.forEach((Task createdTask) -> this.taskRepository.save(createdTask));
        return createdTasks;
    }

    private void assertTasksAreTheSame(Task expectedTask, Task actualTask) {
        Assertions.assertEquals(expectedTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedTask.getLabel(), actualTask.getLabel());
        Assertions.assertEquals(expectedTask.getDescription(), actualTask.getDescription());
        Assertions.assertEquals(expectedTask.isCompleted(), actualTask.isCompleted());
    }

    private void assertTasksAreDifferent(Task expectedTask, Task actualTask) {
        Assertions.assertNotEquals(expectedTask.getId(), actualTask.getId());
        Assertions.assertNotEquals(expectedTask.getLabel(), actualTask.getLabel());
        Assertions.assertNotEquals(expectedTask.getDescription(), actualTask.getDescription());
    }
}
