package com.todoapp.domain.port;

import com.todoapp.domain.model.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoQueryPort {
    Optional<Todo> findById(String id);
    List<Todo> findAll();
    List<Todo> findByCompleted(boolean completed);
    List<Todo> findByCategory(String category);
    List<Todo> findByPriority(String priority);
    List<Todo> findUrgentTodos();
    
    // Para estadísticas
    long countByCompleted(boolean completed);
    long countUrgentTodos();
    long countTotal();
}
