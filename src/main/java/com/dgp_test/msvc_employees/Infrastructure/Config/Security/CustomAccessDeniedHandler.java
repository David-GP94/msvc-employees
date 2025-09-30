package com.dgp_test.msvc_employees.Infrastructure.Config.Security;

import com.dgp_test.msvc_employees.Presentation.Responses.GlobalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        GlobalResponse<String> errorResponse = new GlobalResponse<>(
                false,
                "You do not have permission to access this resource.",
                null
        );

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
