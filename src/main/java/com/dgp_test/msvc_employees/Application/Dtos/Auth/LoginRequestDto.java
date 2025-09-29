package com.dgp_test.msvc_employees.Application.Dtos.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "User name is required.")
    private String username;
    @NotBlank(message = "Password name is required.")
    private String password;
}
