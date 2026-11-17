package com.epam.exception;

import java.io.Serial;

public class EmployeeNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MESSAGE = "Employee not found for id";

    public EmployeeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(Integer employeeId) {
        super(DEFAULT_MESSAGE + " " + employeeId);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotFoundException(Throwable cause) {
        super(cause);
    }
}
