FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el JAR ya compilado
COPY target/todoapp-backend-*.jar app.jar

EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
