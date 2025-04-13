package ru.sfedu.notes.storage;

import ru.sfedu.notes.model.Status;
import ru.sfedu.notes.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private static final Path FILE_PATH = Paths.get("tasks.json");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public void saveTasks(List<Task> tasks) throws IOException {
        StringBuilder json = new StringBuilder();

        json.append("[\n");

        if (tasks.isEmpty()) {
            json.append("]");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);

                json.append("\t{\n");
                json.append("\t\t\"id\":").append(task.getId()).append(",\n");
                json.append("\t\t\"description\":\"").append(task.getDescription()).append("\",\n");
                json.append("\t\t\"status\":\"").append(task.getStatus().toString()).append("\",\n");
                json.append("\t\t\"createdAt\":\"").append(task.getCreatedAt().format(FORMATTER)).append("\",\n");
                json.append("\t\t\"updatedAt\":\"").append(task.getUpdatedAt().format(FORMATTER)).append("\"\n");
                json.append("\t}");

                if (i < tasks.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }

            json.append("]");
        }

        Files.write(FILE_PATH, json.toString().getBytes());
    }

    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        String json = Files.readString(FILE_PATH);
        if (json.equals("[]")) {
            return new ArrayList<>();
        }

        json = json.substring(1, json.length() - 1);

        int start = 0;
        int counter = 0;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                if (counter == 0) {
                    start = i;
                }
                counter++;
            } else if (c == '}') {
                counter--;
                if (counter == 0) {
                    String taskJson = json.substring(start + 1, i);
                    // Разделяем по запятым, но учитываем переносы строк
                    String[] fields = taskJson.split(",\\s*\\n\\s*");

                    String idField = fields[0];
                    int id = Integer.parseInt(idField.substring(idField.indexOf(":") + 1).trim());

                    String descriptionField = fields[1];
                    String description = descriptionField.substring(descriptionField.indexOf(":") + 2, descriptionField.length() - 1).trim();

                    String statusField = fields[2];
                    String statusStr = statusField.substring(statusField.indexOf(":") + 2, statusField.length() - 1).trim();

                    String createdAtField = fields[3];
                    String createdAtStr = createdAtField.substring(createdAtField.indexOf(":") + 2, createdAtField.lastIndexOf("\"")).trim();

                    String updatedAtField = fields[4];
                    String updatedAtStr = updatedAtField.substring(updatedAtField.indexOf(":") + 2, updatedAtField.lastIndexOf("\"")).trim();

                    Task task = new Task();
                    task.setId(id);
                    task.setDescription(description);
                    task.setStatus(Status.valueOf(statusStr));
                    task.setCreatedAt(LocalDateTime.parse(createdAtStr, FORMATTER));
                    task.setUpdatedAt(LocalDateTime.parse(updatedAtStr, FORMATTER));

                    tasks.add(task);
                }
            }
        }

        return tasks;
    }
}