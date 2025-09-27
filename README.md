# msvc-employees

**Author:** David Garcia Prado
**Repository:** [https://github.com/David-GP94/msvc-employees](https://github.com/David-GP94/msvc-employees)

---

## Table of Contents

1. [Project Description](#project-description)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [API Endpoints](#api-endpoints)
5. [Installation](#installation)
6. [Running the Application](#running-the-application)
7. [Testing](#testing)
8. [Swagger Documentation](#swagger-documentation)
9. [Notes](#notes)

---

## Project Description

This project is a microservice built with Spring Boot 2.7.x for managing employee data. It follows modern best practices for REST API design, security with JWT, and microservice architecture. The service connects to a MySQL database and provides CRUD operations for the `Employee` entity.

---

## Features

* CRUD operations for employees
* Search employees by name
* JWT authentication
* Role-based access control (USER and ADMIN)
* Swagger/OpenAPI documentation
* Unit tests with JUnit 5 and Mockito
* Logging and error handling
* Dockerized setup for MySQL and API service

---

## Technologies Used

* Java 17
* Spring Boot 2.7.x
* Spring Data JPA / Hibernate
* Spring Security
* MySQL 8.0
* Maven
* Swagger/OpenAPI (springdoc-openapi-ui)
* JUnit 5 & Mockito
* Docker & Docker Compose

---

## API Endpoints

### Authentication

* `POST /api/auth/login`: Login with username and password to receive a JWT token.

### Employees

* `GET /api/employees`: Retrieve all employees (USER or ADMIN role).
* `GET /api/employees/{id}`: Retrieve a single employee by ID (USER or ADMIN role).
* `POST /api/employees/create`: Create one or multiple employees (ADMIN role).
* `PUT /api/employees/update/{id}`: Update an employee by ID (ADMIN role).
* `DELETE /api/employees/delete/{id}`: Delete an employee by ID (ADMIN role).
* `GET /api/employees/search?name={name}`: Search employees by name (USER or ADMIN role).

---

## Installation

1. Clone the repository:

```bash
git clone https://github.com/David-GP94/msvc-employees.git
cd msvc-employees
```

2. Build the project using Maven:

```bash
mvn clean package
```

3. Ensure Docker and Docker Compose are installed on your system.

---

## Running the Application

1. Start Docker Compose:

```bash
docker-compose up --build
```

2. The API will be available at:
   `http://localhost:8080`

3. MySQL will be available at port 3307 with database `technical_test`.

---

## Testing

Unit tests are implemented using JUnit 5 and Mockito:

```bash
mvn test
```

---

## Swagger Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

This interface allows you to explore all API endpoints, view request/response schemas, and execute test requests.

---

Notes

The JWT token is required for accessing protected endpoints.

Roles: USER and ADMIN.

Ensure the environment variables in Docker Compose are correctly set for database connectivity.

The Dockerfile and docker-compose.yml files are included for easy deployment.

All responses follow a consistent GlobalResponse structure for success, message, and data.

Docker is required to run the application as designed in the deliverables.

---


