package com.dgp_test.msvc_employees.Domain.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
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
    private Integer age;

    @NotBlank(message = "Sex is required.")
    private String sex;

    @NotNull(message = "Birth Date is required.")
    @Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank(message = "Position is required.")
    private String position;

    @CreationTimestamp
    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    private Boolean isActive = true;
}
