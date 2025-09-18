package com.todoapp.application.service;

import com.todoapp.application.usecase.command.CreateTodoCommand;
import com.todoapp.application.usecase.command.UpdateTodoCommand;
import com.todoapp.domain.model.Todo;
import com.todoapp.domain.port.TodoCommandPort;
import com.todoapp.domain.port.TodoQueryPort;
import com.todoapp.infrastructure.adapter.rest.dto.TodoStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoApplicationService {
    private final TodoCommandPort todoCommandPort;
    private final TodoQueryPort todoQueryPort;

    public Todo createTodo(CreateTodoCommand command) {
        return todoCommandPort.create(command.toDomain());
    }

    public Todo updateTodo(UpdateTodoCommand command) {
        Optional<Todo> existingTodo = todoQueryPort.findById(command.id());
        if (existingTodo.isPresent()) {
            Todo updatedTodo = command.toDomain(existingTodo.get());
            return todoCommandPort.update(updatedTodo);
        }
        throw new RuntimeException("Todo not found with id: " + command.id());
    }

    public void deleteTodo(String id) {
        todoCommandPort.delete(id);
    }

    public Optional<Todo> getTodo(String id) {
        return todoQueryPort.findById(id);
    }

    public List<Todo> getAllTodos() {
        return todoQueryPort.findAll();
    }

    public List<Todo> getTodosByFilter(String filter) {
        return switch (filter != null ? filter.toLowerCase() : "all") {
            case "pending" -> todoQueryPort.findByCompleted(false);
            case "completed" -> todoQueryPort.findByCompleted(true);
            case "urgent" -> todoQueryPort.findUrgentTodos();
            default -> todoQueryPort.findAll();
        };
    }

    public List<Todo> getTodosByCategory(String category) {
        return todoQueryPort.findByCategory(category);
    }

    public List<Todo> getTodosByPriority(String priority) {
        return todoQueryPort.findByPriority(priority);
    }

    public TodoStatsDto getTodoStats() {
        long pendingTasks = todoQueryPort.countByCompleted(false);
        long completedTasks = todoQueryPort.countByCompleted(true);
        long urgentTasks = todoQueryPort.countUrgentTodos();
        long totalTasks = todoQueryPort.countTotal();
        
        double progressPercentage = totalTasks == 0 ? 0.0 : 
                (double) completedTasks / totalTasks * 100;
        
        return TodoStatsDto.builder()
                .pendingTasks(pendingTasks)
                .completedTasks(completedTasks)
                .urgentTasks(urgentTasks)
                .progressPercentage(Math.round(progressPercentage * 100.0) / 100.0)
                .build();
    }
}
