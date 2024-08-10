package com.sparta.todo.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ManagerRequest {
    private final String name;
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;
}
