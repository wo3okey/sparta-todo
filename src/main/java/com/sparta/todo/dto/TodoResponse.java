package com.sparta.todo.dto;

import com.sparta.todo.entity.Todo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TodoResponse {
    private final long id;
    private final String todo;
    private final long managerId;
    private final String managerName;
    private final String managerEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static TodoResponse of(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getTodo(),
                entity.getManager().getId(),
                entity.getManager().getName(),
                entity.getManager().getEmail(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

