package com.library.auth.auth.exceptions;

import com.library.auth.exceptions.NotFoundException;

public class UsernameNotFound extends NotFoundException {
    public UsernameNotFound(String message) {
        super(message);
    }
}
