package com.todoapp.infrastructure.adapter.persistence;

import com.todoapp.domain.model.Todo;
import com.todoapp.domain.port.TodoCommandPort;
import com.todoapp.domain.port.TodoQueryPort;
import com.todoapp.infrastructure.adapter.persistence.mapper.TodoEntityMapper;
import com.todoapp.infrastructure.adapter.persistence.repository.TodoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoPersistenceAdapter implements TodoCommandPort, TodoQueryPort {
    private final TodoMongoRepository repository;
    private final TodoEntityMapper mapper;

    @Override
    public Todo create(Todo todo) {
        return mapper.toDomain(
            repository.save(
                mapper.toEntity(todo)
            )
        );
    }

    @Override
    public Todo update(Todo todo) {
        return mapper.toDomain(
            repository.save(
                mapper.toEntity(todo)
            )
        );
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Todo> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Todo> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return repository.findByCompleted(completed).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findByPriority(String priority) {
        return repository.findByPriority(priority).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findUrgentTodos() {
        return repository.findUrgentTodos().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countByCompleted(boolean completed) {
        return repository.countByCompleted(completed);
    }

    @Override
    public long countUrgentTodos() {
        return repository.countUrgentTodos();
    }

    @Override
    public long countTotal() {
        return repository.count();
    }
}
