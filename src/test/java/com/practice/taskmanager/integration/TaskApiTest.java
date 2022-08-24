package com.practice.taskmanager.integration;

import com.practice.taskmanager.TaskManagerConfiguration;
import com.practice.taskmanager.TasksManagerApplication;
import com.practice.taskmanager.models.Task;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskApiTest {
    private static DropwizardAppExtension<TaskManagerConfiguration> EXT = new DropwizardAppExtension<>(
            TasksManagerApplication.class,
            ResourceHelpers.resourceFilePath("test-config.yaml")
    );

    Task taskFirst = null;

    @BeforeEach
    void before() {
        taskFirst = createTask(newTask());
    }

    @AfterEach
    void after() {
        deleteTask(taskFirst.getId());
    }

    @Test
    void add() {

        Task newTask = new Task();
        newTask.setDescription("second");
        newTask.setDate(Instant.now().getEpochSecond());

        Task taskCreated = createTask(newTask);
        assertNotNull(taskCreated);
        assertNotNull(taskCreated.getId());

        //cleanup
        deleteTask(taskCreated.getId());
    }


    @Test
    void get() {

        Client client = EXT.client();
        // Test get all tasks
        List<Task> tasks =
                client.target(String.format("http://localhost:%d/tasks/all", EXT.getLocalPort()))
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Task>>() {
                        });

        assertEquals(1, tasks.size());
        Task task = tasks.get(0);

        // Test get task by taskId
        Task taskFirst = getTask(task.getId());
        assertNotNull(taskFirst);
        assertEquals("first", taskFirst.getDescription());
    }

    @Test
    void update() {
        Task taskExisting = taskFirst;
        assertNotNull(taskExisting.getId());

        Task taskTobeModified = new Task(taskExisting.getId(),
                "second",
                taskExisting.getDate());

        Client client = EXT.client();
        Response response = client.target(
                String.format("http://localhost:%d/tasks/%s", EXT.getLocalPort(), taskExisting.getId()))
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(taskTobeModified, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());

        Task taskModified = getTask(taskExisting.getId());
        assertNotNull(taskModified);
        assertEquals("second", taskModified.getDescription());
    }

    @Test
    void delete() {

        Task taskTobeDeleted = taskFirst;

        Client client = EXT.client();
        Response response = client.target(
                String.format("http://localhost:%d/tasks/%s", EXT.getLocalPort(), taskTobeDeleted.getId()))
                .request(MediaType.APPLICATION_JSON)
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        Task taskDeleted = getTask(taskTobeDeleted.getId());
        assertNull(taskDeleted);
    }

    private static Task newTask() {
        Task task = new Task();
        task.setDescription("first");
        task.setDate(Instant.now().getEpochSecond());

        return task;
    }

    private Task createTask(Task task) {
        Client client = EXT.client();
        Response response = client.target(
                String.format("http://localhost:%d/tasks", EXT.getLocalPort()))
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(task, MediaType.APPLICATION_JSON));


        if (response.getStatus() == Response.Status.CREATED.getStatusCode()
                || response.getStatus() == Response.Status.OK.getStatusCode()) {
             Task created = response.readEntity(Task.class);
             return created;
        } else {
            return null;
        }
    }

    private boolean deleteTask(String taskId) {
        Client client = EXT.client();
        Response response = client.target(
                String.format("http://localhost:%d/tasks/%s", EXT.getLocalPort(), taskId))
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        } else {
            return false;
        }
    }

    private Task getTask(String taskId) {
        Client client = EXT.client();
        Task task =
                client.target(String.format("http://localhost:%d/tasks/%s", EXT.getLocalPort(), taskId))
                        .request(MediaType.APPLICATION_JSON)
                        .get(Task.class);
        return task;
    }

    private Task getTaskByName(String name) {
        Client client = EXT.client();
        Task task =
                client.target(String.format("http://localhost:%d/tasks/name/%s", EXT.getLocalPort(),name))
                        .request(MediaType.APPLICATION_JSON)
                        .get(Task.class);
        return task;
    }

}