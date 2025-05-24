package org.example.service.exception.extend.phone;


import org.example.data.dto.Errors;
import org.example.service.exception.BadDataException;

public class PhoneTakenException extends BadDataException {
    public PhoneTakenException(String message, Errors errorCode) {
        super(message, errorCode);
    }
}


