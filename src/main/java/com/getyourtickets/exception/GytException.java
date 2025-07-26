package com.getyourtickets.exception;

public class GytException extends RuntimeException {
    private final ErrorEnum errorEnum;

    public GytException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }
}
