package org.example.controller.response;

public record ValidationError(
        String field,
        String message
) {
}
