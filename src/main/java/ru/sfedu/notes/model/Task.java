package ru.sfedu.notes.model;

import java.time.LocalDateTime;
import java.util.List;

public class Task {
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private static int counter = 1;

    public Task(String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = counter++;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task() {
        this.id = counter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static void setCounter(List<Task> tasks) {
        int maxId = tasks.stream().mapToInt(Task::getId).max().orElse(0);
        counter = maxId + 1;
    }

    @Override
    public String toString() {
        return "Задача (ID: " + id + ") Дата обновления - " + updatedAt + ":" + description + " [Статус - " + status + "]";
    }
}
