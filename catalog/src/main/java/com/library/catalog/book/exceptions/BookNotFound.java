package com.library.catalog.book.exceptions;

import com.library.catalog.exceptions.NotFoundException;

public class BookNotFound extends NotFoundException {
    public BookNotFound(String message) {
        super(message);
    }
}
