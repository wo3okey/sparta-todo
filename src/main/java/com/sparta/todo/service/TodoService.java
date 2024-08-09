package com.sparta.todo.service;

import com.sparta.todo.dto.TodoRequest;
import com.sparta.todo.dto.TodoResponse;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponse createTodo(TodoRequest request) {
        Todo entity = new Todo(request);
        return TodoResponse.of(todoRepository.save(entity));
    }

    public TodoResponse getTodo(long todoId) {
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponse.of(entity);
    }

    public List<TodoResponse> getTodos(String date, String managerName) {
        List<Todo> entities = todoRepository.search(date, managerName);
        return entities.stream()
                .map(TodoResponse::of)
                .collect(Collectors.toList());
    }
}
