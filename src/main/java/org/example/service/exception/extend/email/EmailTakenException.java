package org.example.service.exception.extend.email;


import org.example.api.responses.Errors;
import org.example.service.exception.BadDataException;

public class EmailTakenException extends BadDataException {
    public EmailTakenException(String message, Errors errorCode) {
        super(message, errorCode);
    }
}

