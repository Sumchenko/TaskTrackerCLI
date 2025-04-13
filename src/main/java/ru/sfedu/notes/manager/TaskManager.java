package ru.sfedu.notes.manager;

import ru.sfedu.notes.model.Status;
import ru.sfedu.notes.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks != null ? tasks : List.of());
    }


    public int addTask(String description) {
        Task task = new Task(
                description,
                Status.TODO,
                LocalDateTime.now(),
                LocalDateTime.now()

        );

        tasks.add(task);
        return task.getId();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }


}
