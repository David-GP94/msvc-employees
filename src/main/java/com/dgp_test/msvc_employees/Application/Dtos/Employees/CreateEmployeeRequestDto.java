package com.dgp_test.msvc_employees.Application.Dtos.Employees;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequestDto {
    @NotBlank(message = "First Name is required.")
    private String firstName;

    private String secondName;

    @NotBlank(message = "Last Name Paternal is required.")
    private String lastNamePaternal;

    private String lastNameMaternal;

    @Positive(message = "Age must be a positive number.")
    @Min(value = 18, message = "Age must be at least 18 years old.")
    @Schema(description = "Employee age (minimum 18 years)", example = "31")
    private Integer age;

    @NotBlank(message = "Gender is required.")
    private String gender;

    @NotNull(message = "Birth Date es obligatorio.")
    @Past(message = "Birth date debe estar en el pasado.")
    @Schema(description = "Format Birth Date dd/MM/yyyy", example = "21/02/1994")
    private LocalDate birthDate;

    @NotBlank(message = "Position is required.")
    private String position;
}
