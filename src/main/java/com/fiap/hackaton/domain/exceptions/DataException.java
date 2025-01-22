package com.fiap.hackaton.domain.exceptions;

import org.springframework.validation.FieldError;

public record DataException(
        String label, String message
) {
    public DataException(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
