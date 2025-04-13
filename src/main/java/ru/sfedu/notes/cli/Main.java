package ru.sfedu.notes.cli;

import ru.sfedu.notes.manager.TaskManager;
import ru.sfedu.notes.model.Task;
import ru.sfedu.notes.storage.TaskStorage;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskStorage taskStorage = new TaskStorage();
        List<Task> tasks = taskStorage.loadTasks();
        TaskManager taskManager = new TaskManager(tasks);
        Task.setCounter(tasks);

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "add":
                    taskManager.addTask(args[++i]);
                    taskStorage.saveTasks(taskManager.getAllTasks());
                    break;

                case "list":
                    tasks = taskManager.getAllTasks();

                    if (tasks.isEmpty()) {
                        System.out.println("Список задач пуст");
                    } else {
                        for (Task task: tasks) {
                            System.out.println(task.toString());
                        }
                    }
                    break;
            }
        }
    }
}