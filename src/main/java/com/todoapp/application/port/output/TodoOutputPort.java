package com.todoapp.application.port.output;

import com.todoapp.domain.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoOutputPort {
    Todo save(Todo todo);
    Optional<Todo> findById(String id);
    List<Todo> findAll();
    void deleteById(String id);
}
