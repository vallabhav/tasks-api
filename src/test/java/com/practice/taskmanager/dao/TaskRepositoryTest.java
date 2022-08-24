package com.practice.taskmanager.dao;

import com.practice.taskmanager.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {

    private LinkedList<Task> taskList;
    private TaskRepository taskRepository;

    @BeforeEach
    void init() {
        taskList =  new LinkedList<>();
        taskList.add(createTask("1001", "task1", Instant.now().getEpochSecond()));
        taskList.add(createTask("1002", "task2", Instant.now().getEpochSecond()));
        taskRepository = new TaskRepository(taskList);
    }

    private Task createTask(String id, String desc, long timestamp) {
        return new Task(id, desc, timestamp);
    }

    @Test
    public void testAdd() {
        assertEquals(taskRepository.getAll().size(), 2, "initial size");
        taskRepository.add("new1", Instant.now().getEpochSecond());
        taskRepository.add("new2", Instant.now().getEpochSecond());
        assertEquals(taskRepository.getAll().size(), 4, "size after new elements");
    }

    @Test
    public void testUpdate() {
        Task taskTobeModified =  taskRepository.getAll().get(0);

        assertEquals(taskTobeModified.getDescription(), "task1", "Task description before update");

        long timestamp = Instant.now().getEpochSecond();
        taskTobeModified.setDescription("new desc");
        taskTobeModified.setDate(timestamp);

        taskRepository.update(taskTobeModified.getId(), taskTobeModified);

        assertEquals(taskTobeModified.getDescription(), "new desc", "Task description post update");
        assertEquals(taskTobeModified.getDate(), timestamp, "Task date post update");
    }

    @Test
    public void testDelete() {
        assertEquals(taskRepository.getAll().size(), 2, "initial size");
        taskRepository.delete("1001");
        assertEquals(taskRepository.getAll().size(), 1, "size after deleting 1st element");
        taskRepository.delete("1002");
        assertEquals(taskRepository.getAll().size(), 0, "size after deleting 2nd element");
    }

    @Test
    public void testDeleteMultiple() {
        assertEquals(taskRepository.getAll().size(), 2, "initial size");
        taskRepository.deleteMultiple(new String[]{"1001", "1002"});
        assertEquals(taskRepository.getAll().size(), 0, "size after deleting 2 tasks");
    }

    @Test
    public void testGet() {
        Task task = taskRepository.get("1001");
        assertNotNull(task);
        assertEquals("task1", task.getDescription());
    }

}