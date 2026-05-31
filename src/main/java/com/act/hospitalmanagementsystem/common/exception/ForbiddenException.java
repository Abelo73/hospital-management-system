package com.act.hospitalmanagementsystem.common.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException() {
        super("Access forbidden");
    }
}
