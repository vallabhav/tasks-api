# tasks-api

It's CRUD rest api service for task management.

## Recommended IDE Setup

[Intellij](https://www.jetbrains.com/idea/) + [Gradle](https://gradle.org/) (version 7.0 or above) + [Java11](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html).

Install docker desktop that installs both docker & kubernates [Docker Desktop](https://www.docker.com/products/docker-desktop/).

## Dropwizard framework

[Dropwizard](https://www.dropwizard.io/en/latest/getting-started.html)

## option1: Project Setup(Development):

### git checkout tasks-api code base

```sh
git clone https://github.com/vallabhav/tasks-api.git
```

### build project

```sh
cd tasks-api
gradle CustomFatJar
```

### Run the application

```sh
java -jar build/libs/tasks-api-1.0-SNAPSHOT.jar server configuration.yaml
```

### Test the service and endpoints using postman or any rest api client tool.

The service runs at http://localhost:8080

## option2: Project Setup(Development): Run within docker

### Build docker

```sh
docker build -t tasks-api .
```

### Verify the created docker image

```sh
docker images | grep "tasks-api"
```

### Run docker image(Test the site at: http://localhost:9002)

```sh
docker run -d -p 9002:80 tasks-api:latest 
```

### check the logs
```sh
docker ps | grep "tasks-api"
docker logs -f <docker ps id>
```