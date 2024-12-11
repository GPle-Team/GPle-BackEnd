package com.gple.backend.global.exception;


import org.springframework.http.HttpStatus;

public class ExpectedException extends RuntimeException {
    private final HttpStatus status;

    public ExpectedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
