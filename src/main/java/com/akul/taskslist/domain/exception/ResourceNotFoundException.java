package com.akul.taskslist.domain.exception;

import java.sql.SQLException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, SQLException e) {
        super(message);
    }

    public ResourceNotFoundException(final String message) {
    }
}
