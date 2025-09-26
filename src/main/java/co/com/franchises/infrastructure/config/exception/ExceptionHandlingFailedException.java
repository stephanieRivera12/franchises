package co.com.franchises.infrastructure.config.exception;

import lombok.Getter;

@Getter
public class ExceptionHandlingFailedException extends RuntimeException {
    public ExceptionHandlingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}