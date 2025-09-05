package com.todolist.service;

import com.todolist.entities.Task;
import com.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    public Task getTask(Long id) {
        return this.taskRepository.findById(id).orElse(null);
    }

    public List<Task> getTasksByCompleted(boolean completed) {
        return this.taskRepository.findByCompleted(completed);
    }

    public Task createTask(Task task) {
        return this.taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = this.taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setLabel(taskDetails.getLabel());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.isCompleted());
            return this.taskRepository.save(task);
        }
        return null;
    }

    public void deleteTask(Long id) {
        this.taskRepository.deleteById(id);
    }
}
