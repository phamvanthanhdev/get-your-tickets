package com.getyourtickets.exception;

import com.getyourtickets.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        ErrorEnum errorEnum;
        try {
            errorEnum = ErrorEnum.valueOf(ex.getFieldError().getDefaultMessage());
        } catch (IllegalArgumentException e) {
            errorEnum = ErrorEnum.UNKNOWN_ERROR;
        }

        ApiResponse response = ApiResponse.builder()
                .code(errorEnum.getCode())
                .message(errorEnum.getMessage())
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
