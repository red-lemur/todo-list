package com.todolist.controller;

import com.todolist.entities.Task;
import com.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return this.taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return this.taskService.getTask(id);
    }

    @GetMapping("/completed")
    public List<Task> getTasksByCompleted(@RequestParam boolean completed) {
        return this.taskService.getTasksByCompleted(completed);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return this.taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return this.taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
    }
}
