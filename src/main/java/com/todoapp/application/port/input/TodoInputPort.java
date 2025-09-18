package com.todoapp.application.port.input;

import com.todoapp.domain.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoInputPort {
    Todo createTodo(Todo todo);
    Optional<Todo> getTodo(String id);
    List<Todo> getAllTodos();
    Todo updateTodo(Todo todo);
    void deleteTodo(String id);
}
