package org.example.service.exception.extend.user;


import org.example.service.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(String.format(message));
    }
}
