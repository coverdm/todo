package com.example.dawid.todo.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressLint("NewApi")
public class Todo implements Serializable {

    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate create;
    private LocalDate end;

    public Todo(String title, String description, Priority priority, Status status, LocalDate create, LocalDate end) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.create = create;
        this.end = end;
    }

    public void finishTodo(){
        status = Status.DONE;
        end = LocalDate.now();
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

    public LocalDate getCreate() {
        return create;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", create=" + create +
                ", end=" + end +
                '}';
    }
}
