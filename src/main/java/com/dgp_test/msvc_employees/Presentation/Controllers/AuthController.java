package com.dgp_test.msvc_employees.Presentation.Controllers;

import com.dgp_test.msvc_employees.Application.Dtos.Auth.LoginRequestDto;
import com.dgp_test.msvc_employees.Infrastructure.Config.Security.Jwt.JwtUtil;
import com.dgp_test.msvc_employees.Presentation.Responses.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "API for user authentication")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "Login",
            description = "Authenticates a user with their username and password, returning a JWT token if credentials are valid. No prior authentication required."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful. JWT token returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request. Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. Invalid credentials",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
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
