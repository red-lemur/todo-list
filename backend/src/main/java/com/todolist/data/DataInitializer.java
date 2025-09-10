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
        this.taskRepository.save(new Task("Quatrième tâche",
                "Mauris viverra non dolor ut aliquam. In eget ipsum mi. Pellentesque varius dui vel tellus mattis accumsan.",
                true));
        this.taskRepository.save(new Task("Cinquième tâche",
                "Nullam efficitur metus nec sem ornare varius. Donec pharetra congue orci quis scelerisque. Nunc auctor feugiat elit, quis mollis lorem feugiat vitae. Nulla pulvinar commodo convallis.",
                true));
        this.taskRepository.save(new Task("Sixième tâche",
                "Praesent dignissim hendrerit neque eu faucibus. Proin dictum non felis non efficitur. Maecenas nec enim ex. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse ut gravida magna."));
        this.taskRepository.save(new Task("Septième tâche",
                "Curabitur nec justo at libero feugiat faucibus. Ut vitae rhoncus mi. Etiam varius sapien ornare pulvinar mattis.",
                true));
    }
}
