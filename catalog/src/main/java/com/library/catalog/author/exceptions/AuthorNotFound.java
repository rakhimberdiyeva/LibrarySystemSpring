package com.library.catalog.author.exceptions;

public class AuthorNotFound extends RuntimeException {
    public AuthorNotFound(String message) {
        super(message);
    }
}
