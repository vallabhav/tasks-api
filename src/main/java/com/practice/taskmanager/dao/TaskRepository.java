package com.practice.taskmanager.dao;

import com.practice.taskmanager.models.Task;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {

    private LinkedList<Task> tasks;

    public TaskRepository() {
        tasks = new LinkedList<>();
    }

    public TaskRepository(LinkedList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task add(String description, long timestamp) {
        Task task = new Task();
        String taskId = UUID.randomUUID().toString();
        task.setId(UUID.randomUUID().toString());
        task.setDescription(description);
        task.setDate(timestamp);
        tasks.add(task);
        return task;
    }

    public void update(String taskId, Task modifiedTask) {
        modifiedTask.setId(taskId);
        tasks.replaceAll(s ->
                s.getId().equals(taskId) ? modifiedTask : s);
    }

    public void delete(String taskId) {
        Task task = get(taskId);
        if (null != task) {
            tasks.remove(task);
        }
    }

    public void deleteMultiple(String[] taskIds) {
        for (String taskId: taskIds) {
            Task task = get(taskId);
            if (null != task) {
                tasks.remove(task);
            }
        }
    }

    public Task get(String taskId) {
        Task task = tasks.stream()
                .filter(taskRecord -> taskId.equals(taskRecord.getId()))
                .findAny()
                .orElse(null);
        return task;
    }

    public Task getByName(String name) {
        Task task = tasks.stream()
                .filter(taskRecord -> name.equals(taskRecord.getDescription()))
                .findAny()
                .orElse(null);
        return task;
    }

    public List<Task> getAll() {
        return tasks;
    }

    /**
     * cleanup data, needed for integration tests
     */
    public void reset() {
        this.tasks = new LinkedList<>();
    }
}
