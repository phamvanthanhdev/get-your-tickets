package com.getyourtickets.exception;

import com.getyourtickets.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        ApiResponse response = ApiResponse.builder()
                .code(ErrorEnum.UNKNOWN_ERROR.getCode())
                .message(ErrorEnum.UNKNOWN_ERROR.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        ApiResponse response = ApiResponse.builder()
                .code(500)
                .message("Internal Server Error: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
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
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GytException.class)
    public ResponseEntity<ApiResponse> handleGytException(GytException ex) {
        ApiResponse response = ApiResponse.builder()
                .code(ex.getErrorEnum().getCode())
                .message(ex.getErrorEnum().getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
