package com.todoapp.infrastructure.adapter.rest.mapper;

import com.todoapp.application.usecase.command.CreateTodoCommand;
import com.todoapp.application.usecase.command.UpdateTodoCommand;
import com.todoapp.domain.model.Todo;
import com.todoapp.infrastructure.adapter.rest.dto.TodoDto;
import org.springframework.stereotype.Component;

@Component
public class TodoDtoMapper {
    public TodoDto toDto(Todo domain) {
        return TodoDto.builder()
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

    public CreateTodoCommand toCreateCommand(TodoDto dto) {
        return new CreateTodoCommand(
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus(),
                dto.isCompleted(),
                dto.getCategory(),
                dto.getPriority(),
                dto.getDueDate()
        );
    }

    public UpdateTodoCommand toUpdateCommand(String id, TodoDto dto) {
        return new UpdateTodoCommand(
                id,
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus(),
                dto.isCompleted(),
                dto.getCategory(),
                dto.getPriority(),
                dto.getDueDate()
        );
    }
}
