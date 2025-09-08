package com.todolist.controller;

import com.todolist.entity.Task;
import com.todolist.exception.TaskNotFoundException;
import com.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.taskService.getAllTasks();
        HttpStatus httpStatus = tasks.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(tasks);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) throws TaskNotFoundException {
        return this.taskService.getTask(id);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getTasksByCompleted(@RequestParam boolean completed) {
        List<Task> tasks = this.taskService.getTasksByCompleted(completed);
        HttpStatus httpStatus = tasks.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = this.taskService.updateTask(id, taskDetails);
        HttpStatus httpStatus = updatedTask == null ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
