package org.example.controller.advice;

import org.example.controller.response.SimpleResponse;
import org.example.controller.response.ValidationError;
import org.example.controller.response.ValidationErrorResponse;
import org.example.service.exception.BadDataException;
import org.example.service.exception.ForbiddenException;
import org.example.service.exception.NotFoundException;
import org.example.service.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.add(new ValidationError(fieldName, message));
        });

        ex.getBindingResult().getGlobalErrors().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            errors.add(new ValidationError("", message));
        });

        ValidationErrorResponse errorResponse = new ValidationErrorResponse("Validation Error", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<SimpleResponse> handleTokenRefreshException(TokenRefreshException ex) {
        SimpleResponse simpleResponse = new SimpleResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(simpleResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<SimpleResponse> handleNotFoundException(NotFoundException ex) {
        SimpleResponse simpleResponse = new SimpleResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(simpleResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<SimpleResponse> handleForbiddenException(ForbiddenException ex) {
        SimpleResponse simpleResponse = new SimpleResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(simpleResponse);
    }

    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<SimpleResponse> handleBadDataException(BadDataException ex) {
        SimpleResponse simpleResponse = new SimpleResponse(ex.getMessage(), ex.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(simpleResponse);
    }

}


