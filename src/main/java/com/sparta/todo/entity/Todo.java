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
    private String managerName;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Todo(TodoRequest request) {
        this.todo = request.getTodo();
        this.managerName = request.getManagerName();
        this.password = request.getPassword();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void changeId(long id) {
        this.id = id;
    }
}

