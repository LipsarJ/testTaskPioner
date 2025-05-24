package org.example.data.dto;


import java.util.List;

public record ValidationErrorResponse(
        String message,
        List<ValidationError> errors
) {
}

