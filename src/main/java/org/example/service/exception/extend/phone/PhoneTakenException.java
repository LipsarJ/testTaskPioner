package org.example.service.exception.extend.phone;


import org.example.api.responses.Errors;
import org.example.service.exception.BadDataException;

public class PhoneTakenException extends BadDataException {
    public PhoneTakenException(String message, Errors errorCode) {
        super(message, errorCode);
    }
}


