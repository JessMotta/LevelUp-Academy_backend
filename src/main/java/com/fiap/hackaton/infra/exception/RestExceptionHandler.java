package com.fiap.hackaton.infra.exception;

import com.fiap.hackaton.domain.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<DataException>> handleDataRequestException(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors()
                .stream()
                .map(DataException::new)
                .toList();
        log.warn("Data request exception");

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("Entity not found");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND
                )
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ExceptionDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(CredentialsException.class)
    protected ResponseEntity<ExceptionDTO> handleCredentialsException(CredentialsException ex) {
        log.warn("Credentials exception");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                )
        );
    }

    @ExceptionHandler(ImageException.class)
    protected ResponseEntity<ExceptionDTO> handleImageException(ImageException ex) {
        log.warn("Image exception");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(EmailDuplicate.class)
    protected ResponseEntity<ExceptionDTO> handleEmailDuplicate(EmailDuplicate ex) {
        log.warn("Email duplicate");
        return ResponseEntity.badRequest().body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.CONFLICT
                )
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDTO> handleException(Exception ex) {
        log.error("Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDTO(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
        );
    }
}
