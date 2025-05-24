package org.example.controller.response;


import java.util.List;

public record ValidationErrorResponse(
        String message,
        List<ValidationError> errors
) {
}

