# Todo App - Backend

Aplicación de gestión de tareas implementada siguiendo los principios de la Arquitectura Hexagonal (Ports and Adapters).

## Estructura del Proyecto

```
src/main/java/com/todoapp/
├── domain/                 # Capa de Dominio
│   ├── model/             # Entidades y objetos de valor
│   │   └── Todo.java      # Entidad principal de tarea
│   └── port/              # Puertos (interfaces) del dominio
│       ├── TodoCommandPort.java  # Puerto para operaciones de escritura
│       └── TodoQueryPort.java    # Puerto para operaciones de lectura
│
├── application/           # Capa de Aplicación
│   ├── service/          # Servicios de aplicación
│   │   └── TodoApplicationService.java  # Implementación de casos de uso
│   └── usecase/          # Casos de uso
│       └── command/      # Comandos para operaciones
│           ├── CreateTodoCommand.java   # Comando para crear tarea
│           └── UpdateTodoCommand.java   # Comando para actualizar tarea
│
└── infrastructure/        # Capa de Infraestructura
    ├── adapter/          # Adaptadores
    │   ├── persistence/  # Adaptador de persistencia (MongoDB)
    │   │   ├── TodoPersistenceAdapter.java  # Implementación de puertos
    │   │   ├── entity/
    │   │   │   └── TodoEntity.java    # Entidad de MongoDB
    │   │   ├── mapper/
    │   │   │   └── TodoEntityMapper.java  # Conversor Entity ↔ Domain
    │   │   └── repository/
    │   │       └── TodoMongoRepository.java  # Repositorio MongoDB
    │   └── rest/         # Adaptador REST
    │       ├── TodoRestAdapter.java    # Controlador REST
    │       ├── dto/
    │       │   └── TodoDto.java       # DTO para API
    │       └── mapper/
    │           └── TodoDtoMapper.java # Conversor DTO ↔ Domain
    └── config/           # Configuraciones
        └── BeanConfiguration.java  # Configuración de dependencias
```

## Arquitectura

### Capa de Dominio
- **Propósito**: Contiene la lógica de negocio central
- **Componentes**:
  - `Todo.java`: Entidad principal que representa una tarea
  - `TodoCommandPort`: Define operaciones de escritura (Create/Update/Delete)
  - `TodoQueryPort`: Define operaciones de lectura (Read)
- **Características**:
  - Completamente aislada de frameworks externos
  - No tiene dependencias con otras capas
  - Define los contratos (puertos) para comunicación externa

### Capa de Aplicación
- **Propósito**: Orquesta los casos de uso de la aplicación
- **Componentes**:
  - `TodoApplicationService`: Implementa la lógica de aplicación
  - `CreateTodoCommand`: Comando para crear tareas
  - `UpdateTodoCommand`: Comando para actualizar tareas
- **Características**:
  - Implementa casos de uso específicos
  - Coordina el flujo de datos entre adaptadores y dominio
  - Mantiene la lógica de negocio independiente de la infraestructura

### Capa de Infraestructura
- **Propósito**: Proporciona implementaciones técnicas
- **Adaptadores**:
  - **REST (Primary/Driving Adapter)**:
    - `TodoRestAdapter`: Maneja peticiones HTTP
    - `TodoDto`: Objeto de transferencia de datos
    - `TodoDtoMapper`: Convierte entre DTO y modelo de dominio
  - **Persistencia (Secondary/Driven Adapter)**:
    - `TodoPersistenceAdapter`: Implementa operaciones de base de datos
    - `TodoEntity`: Entidad de MongoDB
    - `TodoEntityMapper`: Convierte entre Entity y modelo de dominio
    - `TodoMongoRepository`: Interface para MongoDB

## Flujo de Datos

1. **Entrada de Datos (REST)**:
   ```
   HTTP Request → TodoRestAdapter → TodoDto → TodoDtoMapper → Domain Model
   ```

2. **Procesamiento (Aplicación)**:
   ```
   Command → TodoApplicationService → Domain Ports → Domain Logic
   ```

3. **Persistencia (MongoDB)**:
   ```
   Domain Model → TodoEntityMapper → TodoEntity → TodoMongoRepository → MongoDB
   ```

## Operaciones CRUD

### Create
- **Endpoint**: POST `/api/todos`
- **Flujo**: `TodoRestAdapter.createTodo() → ApplicationService.createTodo() → PersistenceAdapter.create()`

### Read
- **Endpoints**: 
  - GET `/api/todos/{id}`
  - GET `/api/todos` (con filtros opcionales)
  - GET `/api/todos?filter=pending|completed|urgent`
  - GET `/api/todos?category=work|personal|health|learning|finance|social`
  - GET `/api/todos?priority=low|medium|high|urgent`
  - GET `/api/todos/stats` (estadísticas)
- **Flujo**: `TodoRestAdapter.getTodo() → ApplicationService.getTodo() → PersistenceAdapter.findById()`

### Update
- **Endpoint**: PUT `/api/todos/{id}`
- **Flujo**: `TodoRestAdapter.updateTodo() → ApplicationService.updateTodo() → PersistenceAdapter.update()`

### Delete
- **Endpoint**: DELETE `/api/todos/{id}`
- **Flujo**: `TodoRestAdapter.deleteTodo() → ApplicationService.deleteTodo() → PersistenceAdapter.delete()`

## Configuración

### MongoDB
```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI:mongodb://localhost:27017}
      database: ${MONGODB_DATABASE:wipa}
```

#### Configuración de Variables de Entorno

Para usar la base de datos remota, configura estas variables de entorno:

```bash
export MONGODB_URI=mongodb://root:example@localhost:27017
export MONGODB_DATABASE=wipa
```

O crea un archivo `.env` en la raíz del proyecto backend:
```env
MONGODB_URI=mongodb://root:example@localhost:27017
MONGODB_DATABASE=wipa
SERVER_PORT=8080
LOG_LEVEL=INFO
APP_LOG_LEVEL=DEBUG
MONGODB_LOG_LEVEL=INFO
```

### Variables de Entorno
- `MONGODB_URI`: URI completa de MongoDB (default: mongodb://localhost:27017)
- `MONGODB_DATABASE`: Nombre de la base de datos (default: wipa)
- `SERVER_PORT`: Puerto del servidor (default: 8080)
- `LOG_LEVEL`: Nivel de logging global (default: INFO)
- `APP_LOG_LEVEL`: Nivel de logging de la aplicación (default: DEBUG)
- `MONGODB_LOG_LEVEL`: Nivel de logging de MongoDB (default: INFO)

## Ejecución

1. **Requisitos**:
   - Java 17+
   - MongoDB
   - Maven

2. **Comandos**:
   ```bash
   # Configurar variables de entorno
   export MONGODB_URI=mongodb://root:example@localhost:27017
   export MONGODB_DATABASE=wipa
   
   # Compilar
   mvn clean install

   # Ejecutar
   mvn spring-boot:run
   ```

   **Alternativa con variables inline:**
   ```bash
   MONGODB_URI=mongodb://root:example@localhost:27017 MONGODB_DATABASE=wipa mvn spring-boot:run
   ```

3. **Verificación**:
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - API Base URL: `http://localhost:8080/api/todos`

## 🧪 Testing de API para Frontend

**⚠️ IMPORTANTE**: Para que el frontend funcione correctamente, ejecutar:

```bash
# Desde la raíz del proyecto
./test-api.sh
```

Este script verifica que todos los endpoints respondan según lo esperado por el frontend Angular.

### Especificación Completa
Ver `API_SPECIFICATION.md` para la documentación detallada de todos los endpoints, formatos de request/response y códigos de error.

## Beneficios de la Arquitectura

1. **Mantenibilidad**:
   - Código organizado y fácil de entender
   - Separación clara de responsabilidades
   - Fácil de extender y modificar

2. **Testabilidad**:
   - Dominio aislado fácil de probar
   - Adaptadores independientes
   - Mocks simples para puertos

3. **Flexibilidad**:
   - Fácil cambiar implementaciones (ej: MongoDB → PostgreSQL)
   - Agregar nuevos adaptadores sin modificar el dominio
   - Evolución independiente de componentes