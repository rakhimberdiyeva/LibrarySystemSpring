package com.library.auth.auth.exceptions;

import com.library.auth.exceptions.ConflictException;

public class EmailAlreadyExists extends ConflictException {
    public EmailAlreadyExists(String message) {
        super(message);
    }
}
