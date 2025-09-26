package com.dgp_test.msvc_employees.Application.Dtos.Employees;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailedEmployeeDto {
    private EmployeeResponseDto employee;
    private String errorMessage;
}
