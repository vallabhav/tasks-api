# Tasks Management service

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
docker run -d -p 9002:8080 tasks-api:latest 
```

### check the logs
```sh
docker ps | grep "tasks-api"
docker logs -f <docker ps id>
```
## Run unit/integration tests

### unit tests

```sh
gradle test 
```
### integration tests

```sh
gradle integration 
```
## Sample api requests
### create a task:
```sh
headers: Content-Type: application/json
POST http://localhost:9002/tasks
 {
        "description": "task1",
        "date": 1661990400000
}
```
### get all tasks:
```sh
headers: Content-Type: application/json
GET http://localhost:9002/tasks/all
```
### update a task:
```sh
headers: Content-Type: application/json
PUT http://localhost:9002/tasks/68620b97-96cf-4181-bf80-086b4f2c29f0
 {
        "description": "task updated from postman",
        "date": 1661990400000
}
```
### get a particular task:
```sh
headers: Content-Type: application/json
GET http://localhost:9002/tasks/68620b97-96cf-4181-bf80-086b4f2c29f0
```

### delete a single task:
```sh
headers: Content-Type: application/json
DELETE http://localhost:9002/tasks/68620b97-96cf-4181-bf80-086b4f2c29f0
```






