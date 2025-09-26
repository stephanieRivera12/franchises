package co.com.franchises.infrastructure.config.exception;

import java.io.Serializable;
import java.util.Set;

public record ErrorResponse(String code, String message,
                            Set<ApplicationException.Detail> details) implements Serializable {
    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }

}