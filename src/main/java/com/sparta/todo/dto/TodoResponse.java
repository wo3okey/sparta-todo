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
    private final String managerName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static TodoResponse of(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getTodo(),
                entity.getManagerName(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

