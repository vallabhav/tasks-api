FROM gradle:7-jdk11-alpine as builder

WORKDIR /builder

# dependencies
ADD build.gradle .
ADD settings.gradle .
RUN gradle assemble -q

# Build the project
ADD src src
ADD configuration.yaml .
RUN gradle build --stacktrace --info
RUN gradle installDist

### Runtime ###
FROM openjdk:11

EXPOSE 8080
WORKDIR /app

# Copy the binary built in the 1st stage
COPY --from=builder /builder/build/install/tasks-api /app
COPY --from=builder /builder/configuration.yaml /app

ENTRYPOINT ["/app/bin/tasks-api", "com.practice.taskmanager.TasksManagerApplication", "server", "/app/configuration.yaml"]