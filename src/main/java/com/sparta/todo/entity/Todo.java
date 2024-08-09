package com.sparta.todo.entity;

import com.sparta.todo.dto.TodoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {
    private Long id;
    private String todo;
    private Manager manager;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Todo(TodoRequest request, Manager manager) {
        this.todo = request.getTodo();
        this.manager = manager;
        this.password = request.getPassword();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void changeId(long id) {
        this.id = id;
    }

    public void changeTodo(String todo) {
        this.todo = todo;
    }

    public void changeManager(Manager manager) {
        this.manager = manager;
    }
}

