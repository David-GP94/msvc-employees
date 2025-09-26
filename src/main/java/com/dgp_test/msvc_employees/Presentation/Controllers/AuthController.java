package com.dgp_test.msvc_employees.Presentation.Controllers;

import com.dgp_test.msvc_employees.Application.Dtos.Auth.LoginRequestDto;
import com.dgp_test.msvc_employees.Infrastructure.Config.Security.Jwt.JwtUtil;
import com.dgp_test.msvc_employees.Presentation.Responses.GlobalResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public GlobalResponse<String> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String token = jwtUtil.generateToken(authentication.getName());
            return new GlobalResponse<>(true, "Token generated successfully.", token);
        } catch (BadCredentialsException e) {
            return new GlobalResponse<>(false, "Invalid Credentials.", null);
        }
    }
}
