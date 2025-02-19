# PeoplePulse API

## Overview

PeoplePulse API is an orchestration service that loads data from an external dataset into a local in-memory database and provides REST endpoints to fetch user information based on various criteria.

## Features

- Fetch users based on different criteria
- In-memory H2 database for quick access
- OpenAPI (Swagger) documentation
- Exception handling with custom error responses
- Resilience4j integration for fault tolerance

## Technologies Used

- Java 17
- Spring Boot 3.4.2
- Spring Data JPA
- H2 Database
- Resilience4j
- OpenAPI (Swagger)

## Prerequisites

Ensure you have the following installed:

- Java 17
- Maven 3.x

## Installation & Setup

### 1. Clone the Repository

```sh
git clone https://github.com/mr33325/PeoplePulse.io.git
cd PeoplePulse.io\PeoplePulse-BackEnd\peoplepulse-api
```

### 2. Build the Project

```sh
mvn clean install
```

### 3. Run the Application

```sh
mvn spring-boot:run
```

## API Endpoints

### Get All Users

**Request:**

```http
GET /api/users
```

**Response:**

```json
{
  "users": [...],
  "total": 100,
  "skip": 0,
  "limit": 10
}
```

### OpenAPI Documentation

Once the application is running, you can access the API documentation at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Exception Handling

The API uses a global exception handler that returns structured error responses:

```json
{
  "timestamp": "2025-02-19T15:10:26.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input data",
  "path": "/api/users"
}
```

## Contribution

1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit changes (`git commit -m "Add feature"`)
4. Push to branch (`git push origin feature-name`)
5. Create a Pull Request

## License

This project is licensed under the MIT License.

