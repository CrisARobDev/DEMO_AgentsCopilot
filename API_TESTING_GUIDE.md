# API Testing Guide

This guide provides examples for testing the User CRUD API endpoints using curl.

## Prerequisites

Make sure the application is running:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints Examples

### 1. Create a New User

**Request:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2025-10-26T10:00:00.123456",
  "updatedAt": "2025-10-26T10:00:00.123456"
}
```

### 2. Get All Users

**Request:**
```bash
curl -X GET http://localhost:8080/api/users
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "username": "johndoe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "createdAt": "2025-10-26T10:00:00.123456",
    "updatedAt": "2025-10-26T10:00:00.123456"
  }
]
```

### 3. Get User by ID

**Request:**
```bash
curl -X GET http://localhost:8080/api/users/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2025-10-26T10:00:00.123456",
  "updatedAt": "2025-10-26T10:00:00.123456"
}
```

### 4. Get User by Username

**Request:**
```bash
curl -X GET http://localhost:8080/api/users/username/johndoe
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2025-10-26T10:00:00.123456",
  "updatedAt": "2025-10-26T10:00:00.123456"
}
```

### 5. Update User

**Request:**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john.new@example.com",
    "firstName": "John",
    "lastName": "Smith"
  }'
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john.new@example.com",
  "firstName": "John",
  "lastName": "Smith",
  "createdAt": "2025-10-26T10:00:00.123456",
  "updatedAt": "2025-10-26T10:05:00.654321"
}
```

### 6. Delete User

**Request:**
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

**Response (204 No Content):**
```
(No content in response body)
```

## Error Scenarios

### Validation Error

**Request:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "",
    "email": "invalid-email",
    "firstName": "",
    "lastName": ""
  }'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2025-10-26T10:00:00.123456",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "username": "Username is required",
    "email": "Email should be valid",
    "firstName": "First name is required",
    "lastName": "Last name is required"
  }
}
```

### Resource Not Found

**Request:**
```bash
curl -X GET http://localhost:8080/api/users/999
```

**Response (404 Not Found):**
```json
{
  "timestamp": "2025-10-26T10:00:00.123456",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: '999'"
}
```

### Duplicate Resource

**Request (when user with username already exists):**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "another@example.com",
    "firstName": "Another",
    "lastName": "User"
  }'
```

**Response (409 Conflict):**
```json
{
  "timestamp": "2025-10-26T10:00:00.123456",
  "status": 409,
  "error": "Conflict",
  "message": "User already exists with username: 'johndoe'"
}
```

## Testing with Postman

You can import these endpoints into Postman:

1. **Base URL:** `http://localhost:8080`
2. **Headers:** `Content-Type: application/json`
3. Create requests for each endpoint as shown above

## H2 Console Access

Access the H2 database console to view data directly:

1. Navigate to: `http://localhost:8080/h2-console`
2. Use these connection details:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (empty)
3. Click "Connect"

## SQL Queries in H2 Console

View all users:
```sql
SELECT * FROM USERS;
```

View specific user:
```sql
SELECT * FROM USERS WHERE ID = 1;
```

Count users:
```sql
SELECT COUNT(*) FROM USERS;
```
