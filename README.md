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
8. [CI/CD Pipeline](#cicd-pipeline)
9. [Swagger Documentation](#swagger-documentation)
10. [Notes](#notes)

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
* CI/CD integration using GitHub Actions

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
* GitHub Actions

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

## Test Users

To test the login functionality, you can use the following users:

- **Admin User**
   - Username: `admin`
   - Password: `adminpass`

- **Regular User**
   - Username: `user`
   - Password: `userpass`

---
## Installation

1. Clone the repository:

```bash
git clone https://github.com/David-GP94/msvc-employees.git
cd msvc-employees
```

2. Ensure Docker and Docker Compose are installed:

Install Docker

Install Docker Compose
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

Tests are also executed automatically in the CI/CD pipeline to ensure code quality before merging.

---

## CI/CD Pipeline

A GitHub Actions pipeline has been configured to automate build and tests:

```yaml
name: Java CI with Maven

on:
  push:
    branches: [ develop, main ]
  pull_request:
    branches: [ develop, main ]

jobs:
  build:
    runs-on: ubuntu-latest

    services:
      mysql:
        image: mysql:8.0
        env:
          MYSQL_ROOT_PASSWORD: root
          MYSQL_DATABASE: technical_test
        ports:
          - 3307:3306
        options: >-
          --health-cmd='mysqladmin ping --silent'
          --health-interval=10s
          --health-timeout=5s
          --health-retries=3

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: 17
          distribution: temurin

      - name: Build Docker image
        run: docker build -t msvc-employees:latest .

      - name: Build with Maven
        run: mvn clean package --file pom.xml

      - name: Run tests
        run: mvn test
```

**Notes on CI/CD:**

* The MySQL service in the pipeline ensures that unit tests depending on the database run successfully.
* If tests fail, the pipeline stops and the merge to `develop` is blocked.
* For production, the application will connect to the actual MySQL server, so the CI service is only for automated testing.

---
## API Contract

This project includes an OpenAPI 3.0 contract that documents all API endpoints, request/response models, and security requirements.

- The contract file is located at: `docs/api-contract.yaml`
- You can view and interact with the API using Swagger UI or import it into Postman.
- All endpoints require JWT authentication, except `/api/auth/login`.

## Swagger Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

This interface allows you to explore all API endpoints, view request/response schemas, and execute test requests.

---

## Notes

* The JWT token is required for accessing protected endpoints.
* Roles: USER and ADMIN.
* Ensure the environment variables in Docker Compose are correctly set for database connectivity.
* The Dockerfile and docker-compose.yml files are included for easy deployment.
* All responses follow a consistent GlobalResponse structure for success, message, and data.
* Docker is required to run the application as designed in the deliverables.
* CI/CD is implemented to enforce automated builds and tests before merging features.
