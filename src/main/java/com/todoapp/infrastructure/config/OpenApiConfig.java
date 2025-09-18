package com.todoapp.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI todoOpenAPI() {
        Server devServer = new Server()
            .url("http://localhost:" + serverPort)
            .description("Servidor de desarrollo");

        Contact contact = new Contact()
            .name("Equipo de Desarrollo")
            .email("dev@todoapp.com")
            .url("https://todoapp.com");

        License license = new License()
            .name("MIT License")
            .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
            .title("Todo App API")
            .version("1.0.0")
            .description("API RESTful para la gestión de tareas (Todo App)")
            .termsOfService("https://todoapp.com/terms")
            .contact(contact)
            .license(license);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer));
    }
}