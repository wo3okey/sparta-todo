package com.sparta.todo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TodoRequest {
    private final String todo;
    private final Long managerId;
    private final String password;
}
