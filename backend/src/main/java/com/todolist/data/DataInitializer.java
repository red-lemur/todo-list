package com.todolist.data;

import com.todolist.entity.Task;
import com.todolist.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    private TaskRepository taskRepository;

    @PostConstruct
    public void initializeData() {
        this.taskRepository.save(new Task("Première tâche",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eros erat, ultrices a fringilla nec, fermentum at purus."));
        this.taskRepository.save(new Task("Deuxième tâche",
                "Suspendisse quis est ut arcu maximus imperdiet. Maecenas convallis luctus nisi, et porttitor lectus mattis quis.",
                true));
        this.taskRepository.save(new Task("Troisième tâche",
                "Duis tellus nunc, suscipit quis purus non, pulvinar laoreet risus. Praesent rhoncus, lacus sed ornare sagittis, lacus ipsum lacinia tortor, eu fringilla ipsum augue eget leo."));
    }
}
