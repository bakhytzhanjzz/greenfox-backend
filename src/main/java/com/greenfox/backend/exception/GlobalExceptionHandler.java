package com.greenfox.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildError(HttpStatus status, String error, String message, String path, List<String> validationErrors) {
        ApiError apiError = ApiError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .validationErrors(validationErrors)
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        return buildError(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), req.getRequestURI(), null);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiError> handleSecurity(SecurityException ex, HttpServletRequest req) {
        return buildError(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), req.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        return buildError(HttpStatus.BAD_REQUEST, "Validation Error", "Validation failed", req.getRequestURI(), errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), req.getRequestURI(), null);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiError> handleDenied(org.springframework.security.access.AccessDeniedException ex,
                                                 HttpServletRequest req) {
        return buildError(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), req.getRequestURI(), null);
    }

}
