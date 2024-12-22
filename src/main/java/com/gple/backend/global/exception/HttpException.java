package com.gple.backend.global.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException {
    private final HttpStatus status;

    public HttpException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.status = exceptionEnum.getStatus();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
