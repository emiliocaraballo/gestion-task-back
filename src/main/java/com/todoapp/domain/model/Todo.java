package com.todoapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private String id;
    private String title;
    private String description;
    
    @Builder.Default
    private TodoStatus status = TodoStatus.PENDING;
    
    // Mantenemos completed para compatibilidad hacia atrás
    private boolean completed;
    
    private String category;
    private String priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Helper methods - sincronizado con status enum
    public boolean isCompleted() {
        return status != null ? status.isCompleted() : completed;
    }
    
    public boolean isInProgress() {
        return status != null ? status.isInProgress() : false;
    }
    
    public boolean isPending() {
        return status != null ? status.isPending() : !completed;
    }
    
    public boolean isRejected() {
        return status != null ? status.isRejected() : false;
    }
    
    // Sincronizar campo completed con status para compatibilidad
    public void setStatus(TodoStatus status) {
        this.status = status;
        this.completed = status != null && status.isCompleted();
    }
}