package com.dgp_test.msvc_employees.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(
    name = "employees",
    uniqueConstraints = @UniqueConstraint(
            name = "uk_employee_name_age",
            columnNames = {"first_name", "last_name_paternal", "last_name_maternal", "age"}
    )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First Name is required.")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @NotBlank(message = "Last Name Paternal is required.")
    @Column(name = "last_name_paternal")
    private String lastNamePaternal;

    @Column(name = "last_name_maternal")
    private String lastNameMaternal;

    @Positive(message = "Age must be a positive number.")
    @Min(value = 18, message = "Age must be at least 18 years old.")
    @Schema(description = "Employee age (minimum 18 years)", example = "31")
    private Integer age;

    @NotBlank(message = "Gender is required.")
    private String gender;

    @NotNull(message = "Birth Date is required.")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank(message = "Position is required.")
    private String position;

    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    private Boolean isActive = true;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now(ZoneId.of("America/Mexico_City"));
    }
}
