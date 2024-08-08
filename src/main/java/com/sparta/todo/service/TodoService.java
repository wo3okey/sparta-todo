package com.sparta.todo.service;

import com.sparta.todo.dto.TodoRequest;
import com.sparta.todo.dto.TodoResponse;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponse createTodo(TodoRequest request) {
        Todo entity = new Todo(request);
        return TodoResponse.of(todoRepository.save(entity));
    }
}
