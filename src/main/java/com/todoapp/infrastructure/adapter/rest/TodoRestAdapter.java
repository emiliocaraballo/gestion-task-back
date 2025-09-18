package com.todoapp.infrastructure.adapter.rest;

import com.todoapp.application.service.TodoApplicationService;
import com.todoapp.infrastructure.adapter.rest.dto.TodoDto;
import com.todoapp.infrastructure.adapter.rest.dto.TodoStatsDto;
import com.todoapp.infrastructure.adapter.rest.mapper.TodoDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Tag(name = "Todo", description = "API para la gestión de tareas")
public class TodoRestAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TodoRestAdapter.class);
    
    private final TodoApplicationService applicationService;
    private final TodoDtoMapper mapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crear una nueva tarea")
    @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente")
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody TodoDto dto) {
        logger.info("Creando nueva tarea: {}", dto.getTitle());
        var command = mapper.toCreateCommand(dto);
        return ResponseEntity.status(201).body(
            mapper.toDto(
                applicationService.createTodo(command)
            )
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarea por ID")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada")
    public ResponseEntity<TodoDto> getTodo(@PathVariable String id) {
        logger.info("Buscando tarea con ID: {}", id);
        return applicationService.getTodo(id)
                .map(todo -> ResponseEntity.ok(mapper.toDto(todo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Obtener todas las tareas con filtros opcionales")
    @ApiResponse(responseCode = "200", description = "Lista de tareas")
    public ResponseEntity<List<TodoDto>> getAllTodos(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String priority) {
        logger.info("Recuperando tareas con filtros - filter: {}, category: {}, priority: {}", 
                    filter, category, priority);
        
        List<TodoDto> todos;
        
        if (filter != null) {
            todos = applicationService.getTodosByFilter(filter).stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } else if (category != null) {
            todos = applicationService.getTodosByCategory(category).stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } else if (priority != null) {
            todos = applicationService.getTodosByPriority(priority).stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        } else {
            todos = applicationService.getAllTodos().stream()
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarea")
    @ApiResponse(responseCode = "200", description = "Tarea actualizada")
    public ResponseEntity<TodoDto> updateTodo(
            @PathVariable String id,
            @Valid @RequestBody TodoDto dto) {
        logger.info("Actualizando tarea con ID: {}", id);
        var command = mapper.toUpdateCommand(id, dto);
        return ResponseEntity.ok(
            mapper.toDto(
                applicationService.updateTodo(command)
            )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarea")
    @ApiResponse(responseCode = "204", description = "Tarea eliminada")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        logger.info("Eliminando tarea con ID: {}", id);
        applicationService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de las tareas")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    public ResponseEntity<TodoStatsDto> getTodoStats() {
        logger.info("Recuperando estadísticas de tareas");
        var stats = applicationService.getTodoStats();
        return ResponseEntity.ok(stats);
    }
}
