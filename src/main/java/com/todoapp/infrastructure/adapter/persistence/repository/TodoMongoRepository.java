package com.todoapp.infrastructure.adapter.persistence.repository;

import com.todoapp.infrastructure.adapter.persistence.entity.TodoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoMongoRepository extends MongoRepository<TodoEntity, String> {
    
    // Filtros por estado
    List<TodoEntity> findByCompleted(boolean completed);
    
    // Filtros por categoría
    List<TodoEntity> findByCategory(String category);
    
    // Filtros por prioridad
    List<TodoEntity> findByPriority(String priority);
    
    // Filtros combinados
    List<TodoEntity> findByCompletedAndCategory(boolean completed, String category);
    List<TodoEntity> findByCompletedAndPriority(boolean completed, String priority);
    List<TodoEntity> findByCategoryAndPriority(String category, String priority);
    
    // Tareas urgentes (prioridad alta o urgente) que NO están completadas
    // Importante: Solo se consideran urgentes las tareas activas, no las completadas
    @Query("{ 'priority': { $in: ['high', 'urgent'] }, 'completed': false }")
    List<TodoEntity> findUrgentTodos();
    
    // Contar por estado
    long countByCompleted(boolean completed);
    
    // Contar tareas urgentes que NO están completadas
    // Importante: Las estadísticas de urgentes solo incluyen tareas pendientes
    @Query(value = "{ 'priority': { $in: ['high', 'urgent'] }, 'completed': false }", count = true)
    long countUrgentTodos();
}
