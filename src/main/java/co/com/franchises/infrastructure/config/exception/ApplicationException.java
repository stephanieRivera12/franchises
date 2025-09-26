package co.com.franchises.infrastructure.config.exception;

import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Getter
public class ApplicationException extends RuntimeException {
    private final String code;
    private final Set<Detail> details;

    public ApplicationException(String code, String message, Set<Detail> details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public record Detail(String code, String message) implements Serializable {
    }
}