package org.example.service.exception.extend.user;


import org.example.api.responses.Errors;
import org.example.service.exception.BadDataException;

public class UsernameTakenException extends BadDataException {
    public UsernameTakenException(String message, Errors errorCode) {
        super(message, errorCode);
    }
}
