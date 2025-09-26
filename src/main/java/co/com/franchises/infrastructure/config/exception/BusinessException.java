package co.com.franchises.infrastructure.config.exception;

import java.util.Set;

public class BusinessException extends ApplicationException {
    public BusinessException(String code, String message, Set<Detail> details) {
        super(code, message, details);
    }

    public BusinessException(String message) {
        super("400", message, null);
    }
}
