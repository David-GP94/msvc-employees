package com.dgp_test.msvc_employees.Application.Dtos.Employees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchCreationResultDto {
    private List<EmployeeResponseDto> successful;
    private List<FailedEmployeeDto> failed;
}
