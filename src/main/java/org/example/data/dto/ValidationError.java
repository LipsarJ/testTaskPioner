package org.example.data.dto;

public record ValidationError(
        String field,
        String message
) {
}
