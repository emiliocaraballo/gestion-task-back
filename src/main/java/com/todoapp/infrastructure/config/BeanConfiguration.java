package com.todoapp.infrastructure.config;

import com.todoapp.domain.port.TodoCommandPort;
import com.todoapp.domain.port.TodoQueryPort;
import com.todoapp.application.service.TodoApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    
    @Bean
    TodoApplicationService todoApplicationService(
            TodoCommandPort todoCommandPort,
            TodoQueryPort todoQueryPort) {
        return new TodoApplicationService(todoCommandPort, todoQueryPort);
    }
}