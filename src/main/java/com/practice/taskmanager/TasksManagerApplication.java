package com.practice.taskmanager;

import com.practice.taskmanager.dao.TaskRepository;
import com.practice.taskmanager.services.TaskService;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TasksManagerApplication extends Application<TaskManagerConfiguration> {

    public static void main(String[] args)  {
        try{
            new TasksManagerApplication().run(args);
        } catch (Exception ex) {

        }
    }

    @Override
    public void run(TaskManagerConfiguration configuration, Environment environment) throws Exception {
        TaskRepository taskRepository = new TaskRepository();
        TaskService taskService = new TaskService(taskRepository);

        environment
                .jersey()
                .register(taskService);
    }

    @Override
    public void initialize(Bootstrap<TaskManagerConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        super.initialize(bootstrap);
    }
}
