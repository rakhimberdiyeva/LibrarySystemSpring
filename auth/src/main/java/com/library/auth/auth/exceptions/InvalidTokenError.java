package com.library.auth.auth.exceptions;

import com.library.auth.exceptions.UnauthorizedException;

public class InvalidTokenError extends UnauthorizedException {
    public InvalidTokenError(String message) {
        super(message);
    }
}
