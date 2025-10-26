# JPA CRUD Demo Application

A Spring Boot application demonstrating basic CRUD operations for a User entity using JPA and H2 database.

## Features

- **User Entity Management**: Complete CRUD operations for User entity
- **JPA Integration**: Using Spring Data JPA for database operations
- **H2 Database**: In-memory database for development and testing
- **REST API**: RESTful endpoints for user management
- **Validation**: Input validation using Bean Validation
- **Exception Handling**: Global exception handling with custom exceptions
- **Transaction Management**: Proper transaction boundaries
- **Comprehensive Testing**: Unit tests and integration tests

## Technologies

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database
- Maven
- JUnit 5
- Mockito

## Project Structure

```
src/
├── main/
│   ├── java/com/demo/jpacrud/
│   │   ├── controller/         # REST controllers
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── entity/            # JPA entities
│   │   ├── exception/         # Custom exceptions
│   │   ├── repository/        # JPA repositories
│   │   ├── service/           # Business logic
│   │   └── JpaCrudDemoApplication.java
│   └── resources/
│       └── application.properties
└── test/
    ├── java/com/demo/jpacrud/
    │   ├── controller/        # Integration tests
    │   └── service/           # Unit tests
    └── resources/
        └── application.properties
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository
```bash
git clone https://github.com/CrisARobDev/DEMO_AgentsCopilot.git
cd DEMO_AgentsCopilot
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests

```bash
mvn test
```

## API Endpoints

### Create User
```
POST /api/users
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

### Get All Users
```
GET /api/users
```

### Get User by ID
```
GET /api/users/{id}
```

### Get User by Username
```
GET /api/users/username/{username}
```

### Update User
```
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john.new@example.com",
  "firstName": "John",
  "lastName": "Smith"
}
```

### Delete User
```
DELETE /api/users/{id}
```

## H2 Console

The H2 database console is available at: `http://localhost:8080/h2-console`

Connection details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Entity Mappings

### User Entity

| Field | Type | Constraints |
|-------|------|-------------|
| id | Long | Primary Key, Auto-generated |
| username | String | Not null, Unique, 3-50 characters |
| email | String | Not null, Unique, Valid email format |
| firstName | String | Not null, Max 50 characters |
| lastName | String | Not null, Max 50 characters |
| createdAt | LocalDateTime | Auto-generated on create |
| updatedAt | LocalDateTime | Auto-updated on modification |

## Transaction Management

- `@Transactional` annotation used on service methods
- Read-only transactions for query methods
- Write transactions for create, update, delete operations
- Automatic rollback on exceptions

## Error Handling

The application uses global exception handling with custom exceptions:

- **ResourceNotFoundException**: Returns 404 when resource not found
- **DuplicateResourceException**: Returns 409 when attempting to create duplicate resources
- **MethodArgumentNotValidException**: Returns 400 with validation errors

## Test Coverage

### Unit Tests (UserServiceTest)
- Create user with valid data
- Create user with duplicate username/email
- Get all users
- Get user by ID
- Get user by username
- Update user
- Delete user
- Error scenarios

### Integration Tests (UserControllerIntegrationTest)
- Full REST API endpoint testing
- Database integration testing
- Validation testing
- Error handling testing

## License

This is a demo project for educational purposes.