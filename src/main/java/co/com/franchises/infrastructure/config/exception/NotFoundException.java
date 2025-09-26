package co.com.franchises.infrastructure.config.exception;

import java.util.Set;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String code, String message, Set<Detail> details) {
        super(code, message, details);
    }

    public NotFoundException(String message) {
        super("404", message, null);
    }
}
