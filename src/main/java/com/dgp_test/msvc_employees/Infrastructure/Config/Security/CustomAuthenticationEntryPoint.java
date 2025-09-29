package com.dgp_test.msvc_employees.Infrastructure.Config.Security;

import com.dgp_test.msvc_employees.Presentation.Responses.GlobalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 en lugar de 403
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        GlobalResponse<String> errorResponse = new GlobalResponse<>(
                false,
                "Missing or invalid JWT token: " + authException.getMessage(),
                null
        );

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
