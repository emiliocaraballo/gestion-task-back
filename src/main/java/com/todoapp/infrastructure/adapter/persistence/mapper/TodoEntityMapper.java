package com.todoapp.infrastructure.adapter.persistence.mapper;

import com.todoapp.domain.model.Todo;
import com.todoapp.domain.model.TodoStatus;
import com.todoapp.infrastructure.adapter.persistence.entity.TodoEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoEntityMapper {
    public Todo toDomain(TodoEntity entity) {
        return Todo.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(TodoStatus.fromValue(entity.getStatus()))
                .completed(entity.isCompleted()) // Para compatibilidad hacia atrás
                .category(entity.getCategory())
                .priority(entity.getPriority())
                .dueDate(entity.getDueDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public TodoEntity toEntity(Todo domain) {
        return TodoEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .status(domain.getStatus().getValue())
                .completed(domain.isCompleted()) // Para compatibilidad hacia atrás
                .category(domain.getCategory())
                .priority(domain.getPriority())
                .dueDate(domain.getDueDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
