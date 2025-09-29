# Evidence of Functionality

**Project:** MSVC Employees  
**Author:** David Garcia Prado

This document provides visual evidence that the microservice is working correctly, including endpoints, unit tests, and CI/CD pipeline execution.

---

## Table of Contents

1. [Swagger UI Endpoints](#swagger-ui-endpoints)
2. [API Requests](#api-requests)
    - [Get All Employees](#get-all-employees)
    - [Get Employee by ID](#get-employee-by-id)
    - [Create Employee](#create-employee)
    - [Update Employee](#update-employee)
    - [Delete Employee](#delete-employee)
    - [Search Employees](#search-employees)
3. [Unit Tests](#unit-tests)
4. [GitHub Actions Pipeline](#github-actions-pipeline)
5. [Database Evidence (Optional)](#database-evidence-optional)

---

## Swagger UI Endpoints

Swagger UI showing all available endpoints.

![Swagger UI](docs/screenshots/swagger_ui.png)

---

## API Requests

### User Login

POST request to authenticate a user and receive a JWT token.

![User Login](docs/screenshots/user_login.png)

### Get All Employees

GET request to retrieve all employees.

![Get All Employees](docs/screenshots/get_all_employees.png)

### Get Employee by ID

GET request to retrieve a single employee by ID.

![Get Employee by ID](docs/screenshots/get_employee_by_id.png)

### Create Employee

POST request to create one or multiple employees.

![Create Employee](docs/screenshots/create_employee.png)

### Update Employee

PUT request to update an employee by ID.

![Update Employee](docs/screenshots/update_employee.png)

### Delete Employee

DELETE request to remove an employee by ID.

![Delete Employee](docs/screenshots/delete_employee.png)

### Search Employees

GET request to search employees by name.

![Search Employees](docs/screenshots/search_employees.png)

---

## Unit Tests

Evidence of unit tests execution using JUnit and Mockito, all passing successfully.

![Unit Tests](docs/screenshots/mvn_tests_passed.png)

---

## GitHub Actions Pipeline

Execution of the CI/CD pipeline in GitHub Actions, showing build, test, and deployment steps.

![Pipeline](docs/screenshots/github_actions_pipeline.png)

---


> All images should be placed in the `docs/screenshots/` folder and referenced here.  
> Use descriptive file names for clarity and easy navigation.
