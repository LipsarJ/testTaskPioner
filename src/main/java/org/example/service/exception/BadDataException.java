package org.example.service.exception;


import org.example.data.dto.Errors;

public class BadDataException extends RuntimeException {
    private final Errors errorCode;

    public BadDataException(String message, Errors errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public Errors getErrorCode() {
        return errorCode;
    }
}
