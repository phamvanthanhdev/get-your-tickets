package com.getyourtickets.exception;

import com.getyourtickets.dto.ApiResponse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        log.error("[Exception] ", ex);
        ErrorEnum errorEnum = ErrorEnum.UNKNOWN_ERROR;
        ApiResponse response = ApiResponse.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .build();
        return new ResponseEntity<>(response, errorEnum.getHttpStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        log.error("[RuntimeException] ", ex);
        ErrorEnum errorEnum = ErrorEnum.UNKNOWN_ERROR;
        ApiResponse response = ApiResponse.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .build();
        return new ResponseEntity<>(response, errorEnum.getHttpStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("[ValidationException] ", ex);

        Map attributes = null;

        ErrorEnum errorEnum;
        try {
            ConstraintViolation constraintValidator = ex.getBindingResult()
                    .getAllErrors().get(0).unwrap(ConstraintViolation.class);
            attributes = constraintValidator.getConstraintDescriptor().getAttributes();

            errorEnum = ErrorEnum.valueOf(ex.getFieldError().getDefaultMessage());
        } catch (IllegalArgumentException e) {
            errorEnum = ErrorEnum.UNKNOWN_ERROR;
        }

        String message = errorEnum.getMessage();
        if (Objects.nonNull(attributes)) {
            if (message.contains("{min}")) {
                message = message.replace("{min}", String.valueOf(attributes.get("min")));
            }
            if (message.contains("{max}")) {
                message = message.replace("{max}", String.valueOf(attributes.get("max")));
            }
        }

        ApiResponse response = ApiResponse.builder()
                .code(errorEnum.getCode())
                .message(message)
                .build();
        return new ResponseEntity<>(response, errorEnum.getHttpStatusCode());
    }

    @ExceptionHandler(GytException.class)
    public ResponseEntity<ApiResponse> handleGytException(GytException ex) {
        log.error("[GytException] ", ex);
        ApiResponse response = ApiResponse.builder()
                .code(ex.getErrorEnum().getCode())
                .message(ex.getErrorEnum().getMessage())
                .build();
        return new ResponseEntity<>(response, ex.getErrorEnum().getHttpStatusCode());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        log.error("[AuthorizationDeniedException] ", ex);
        ErrorEnum errorEnum = ErrorEnum.UNAUTHORIZED;
        ApiResponse response = ApiResponse.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
                .build();
        return new ResponseEntity<>(response, errorEnum.getHttpStatusCode());
    }


}
