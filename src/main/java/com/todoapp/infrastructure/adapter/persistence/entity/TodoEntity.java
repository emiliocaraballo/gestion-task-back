package com.todoapp.infrastructure.adapter.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "todos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity {
    @Id
    private String id;
    private String title;
    private String description;
    
    // Nuevo campo de estado como String para MongoDB
    @Builder.Default
    private String status = "pending";
    
    // Mantenemos completed para compatibilidad hacia atrás
    private boolean completed;
    
    private String category;
    private String priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
