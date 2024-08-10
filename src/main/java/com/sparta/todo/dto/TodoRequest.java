package com.sparta.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TodoRequest {
    @NotBlank(message = "할일 제목은 필수 값입니다.")
    @Size(max = 200, message = "할일 제목은 최대 200자 이내여야 합니다.")
    private final String todo;
    private final Long managerId;
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private final String password;
}
