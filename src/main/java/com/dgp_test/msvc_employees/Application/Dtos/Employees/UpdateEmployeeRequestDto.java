package com.dgp_test.msvc_employees.Application.Dtos.Employees;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmployeeRequestDto {
    private String firstName;

    private String secondName;

    private String lastNamePaternal;

    private String lastNameMaternal;

    @Positive(message = "Age must be a positive number.")
    @Min(value = 18, message = "Age must be at least 18 years old.")
    @Schema(description = "Employee age (minimum 18 years)", example = "31")
    private Integer age;

    private String gender;

    private LocalDate birthDate;

    private String position;

    private Boolean isActive;
}
