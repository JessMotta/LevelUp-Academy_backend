package com.fiap.hackaton.domain.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(String message, HttpStatus status) {
}
