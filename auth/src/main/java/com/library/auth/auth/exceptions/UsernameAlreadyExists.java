package com.library.auth.auth.exceptions;

import com.library.auth.exceptions.ConflictException;

public class UsernameAlreadyExists extends ConflictException {
    public UsernameAlreadyExists(String message) {
        super(message);
    }
}
