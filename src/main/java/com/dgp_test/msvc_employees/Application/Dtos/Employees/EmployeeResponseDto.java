package com.dgp_test.msvc_employees.Application.Dtos.Employees;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
    private String firstName;

    private String secondName;

    private String lastNamePaternal;

    private String lastNameMaternal;

    private Integer age;

    private String gender;

    private LocalDate birthDate;

    private String position;
}
