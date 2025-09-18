package com.todoapp.application.usecase.command;

import com.todoapp.domain.model.Todo;
import com.todoapp.domain.model.TodoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateTodoCommand(
    @NotBlank(message = "El ID es obligatorio")
    String id,
    
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    String title,
    
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    String description,
    
    @Pattern(regexp = "pending|in_progress|completed|rejected", 
             message = "Estado debe ser uno de: pending, in_progress, completed, rejected")
    String status,
    
    boolean completed,
    
    @Pattern(regexp = "frontend|backend|database|testing|devops|documentation|bugfix|feature|security|performance", 
             message = "Categoría debe ser uno de: frontend, backend, database, testing, devops, documentation, bugfix, feature, security, performance")
    String category,
    
    @Pattern(regexp = "low|medium|high|urgent", 
             message = "Prioridad debe ser uno de: low, medium, high, urgent")
    String priority,
    
    LocalDate dueDate
) {
    public Todo toDomain(Todo existingTodo) {
        TodoStatus todoStatus = status != null ? TodoStatus.fromValue(status) : existingTodo.getStatus();
        return Todo.builder()
                .id(id)
                .title(title)
                .description(description)
                .status(todoStatus)
                .completed(completed)
                .category(category != null ? category : existingTodo.getCategory())
                .priority(priority != null ? priority : existingTodo.getPriority())
                .dueDate(dueDate != null ? dueDate : existingTodo.getDueDate())
                .createdAt(existingTodo.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
