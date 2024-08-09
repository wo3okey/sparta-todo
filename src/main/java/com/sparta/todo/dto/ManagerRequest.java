package com.sparta.todo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ManagerRequest {
    private final String name;
    private final String email;
}
