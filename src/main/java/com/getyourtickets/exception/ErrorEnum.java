package com.getyourtickets.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorEnum {
    UNKNOWN_ERROR(500, "Unknown error occurred"),
    INVALID_REQUEST(400, "Invalid request"),
    FND_PLAN_NOT_FOUND(404, "FndPlan not found"),
    INVALID_FND_PLAN_NAME(400, "Name must be between 3 and 255 characters"),
    INVALID_FND_PLAN_SALE_PRICE(400, "Sale price must be at least 1000"),;

    private int code;
    private String message;
}
