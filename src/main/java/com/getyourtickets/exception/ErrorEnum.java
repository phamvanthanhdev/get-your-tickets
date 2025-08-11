package com.getyourtickets.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorEnum {
    UNKNOWN_ERROR(500, "Internal sever error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST(400, "Invalid request", HttpStatus.BAD_REQUEST),
    FND_PLAN_NOT_FOUND(404, "FndPlan not found", HttpStatus.BAD_REQUEST),
    INVALID_FND_PLAN_NAME(400, "Name must be between 3 and 255 characters", HttpStatus.BAD_REQUEST),
    INVALID_FND_PLAN_SALE_PRICE(400, "Sale price must be at least 1000", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(404, "User not found", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(409, "User already exists", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED(401, "Authentication failed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(403, "Unauthorized access", HttpStatus.FORBIDDEN),
    INVALID_EMAIL(400, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PERMISSION_NAME(400, "The permission names must be not contain spaces.", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
