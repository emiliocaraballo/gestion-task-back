package com.todoapp.domain.port;

import com.todoapp.domain.model.Todo;

public interface TodoCommandPort {
    Todo create(Todo todo);
    Todo update(Todo todo);
    void delete(String id);
}
