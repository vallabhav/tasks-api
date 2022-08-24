package com.practice.taskmanager.models;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    private String id;
    private String description;
    private long date;

    public Task() {
    }

    public Task(String id, String description, long date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonProperty
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
