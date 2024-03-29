package com.akul.taskslist.domain.exception;

import java.sql.SQLException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String message,
                                     final SQLException e) {
        super(message);
    }

    public ResourceNotFoundException(final String message) {
    }
}
