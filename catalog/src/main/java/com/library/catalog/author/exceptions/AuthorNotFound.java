package com.library.catalog.author.exceptions;

import com.library.catalog.exceptions.NotFoundException;

public class AuthorNotFound extends NotFoundException {
    public AuthorNotFound(String message) {
        super(message);
    }
}
