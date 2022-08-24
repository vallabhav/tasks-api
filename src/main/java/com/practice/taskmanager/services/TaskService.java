package com.practice.taskmanager.services;

import com.practice.taskmanager.dao.TaskRepository;
import com.practice.taskmanager.models.Task;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GET
    @Path("/all")
    public List<Task> getTasks() {
        return taskRepository.getAll();
    }

    @GET
    @Path("/{taskId}")
    public Task getTask(@PathParam("taskId") String taskId) {
        return taskRepository.get(taskId);
    }

    @GET
    @Path("/name/{taskName}")
    public Task getTaskByName(@PathParam("{taskName}") String taskName) {
        return taskRepository.getByName(taskName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Task add(Task task) {
        Task createdTask =  taskRepository.add(task.getDescription(), task.getDate());
        return createdTask;
    }

    @DELETE
    @Path("/{taskId}")
    public void delete(@PathParam("taskId") String taskId) {
        taskRepository.delete(taskId);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteMultiple(String[] taskIds) {
        taskRepository.deleteMultiple(taskIds);
    }

    @PUT
    @Path("/{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("taskId") String taskId, Task task) {
        taskRepository.update(taskId, task);
    }
}
