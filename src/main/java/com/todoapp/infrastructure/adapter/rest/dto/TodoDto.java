package com.todoapp.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private String id;
    
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;
    
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String description;
    
    // Nuevo campo de estado
    @Pattern(regexp = "pending|in_progress|completed|rejected", 
             message = "Estado debe ser uno de: pending, in_progress, completed, rejected")
    private String status;
    
    // Mantenemos completed para compatibilidad hacia atrás
    private boolean completed;
    
    @Pattern(regexp = "frontend|backend|database|testing|devops|documentation|bugfix|feature|security|performance", 
             message = "Categoría debe ser uno de: frontend, backend, database, testing, devops, documentation, bugfix, feature, security, performance")
    private String category;
    
    @Pattern(regexp = "low|medium|high|urgent", 
             message = "Prioridad debe ser uno de: low, medium, high, urgent")
    private String priority;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
