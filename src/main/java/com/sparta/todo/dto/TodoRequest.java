package com.sparta.todo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TodoRequest {
    private final String todo;
    private final String managerName;
    private final String password;
}
