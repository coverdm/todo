package com.example.dawid.todo.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressLint("NewApi")
public class Task implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime create;
    private LocalDateTime finished;
    private LocalDateTime modified;

    public Task(String title, String description, Priority priority, Status status, LocalDateTime create) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.create = create;
    }

    public Task(Integer id, String title, String description, Priority priority, Status status, LocalDateTime create, LocalDateTime finished, LocalDateTime modified) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.create = create;
        this.finished = finished;
        this.modified = modified;
    }

    public void finishTodo(){
        status = Status.DONE;
        finished = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreate() {
        return create;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void updateTitle(String title) {
        this.title = title;
        setWhenModified();
    }

    private void setWhenModified() {
        this.modified = LocalDateTime.now();
    }

    public void updateDescription(String description) {
        this.description = description;
        setWhenModified();
    }

    public void updatePriority(Priority priority) {
        this.priority = priority;
        setWhenModified();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", create=" + create +
                ", finished=" + finished +
                ", modified=" + modified +
                '}';
    }

    public LocalDateTime getModified() {
        return modified;
    }
}
