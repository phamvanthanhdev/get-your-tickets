package com.getyourtickets.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourtickets.dto.ApiResponse;
import com.getyourtickets.exception.ErrorEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorEnum errorEnum = ErrorEnum.AUTHENTICATION_FAILED;

        response.setContentType("application/json");
        response.setStatus(errorEnum.getHttpStatusCode().value());

        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
