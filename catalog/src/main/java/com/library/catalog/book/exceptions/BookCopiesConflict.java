package com.library.catalog.book.exceptions;

import com.library.catalog.exceptions.ConflictException;

public class BookCopiesConflict extends ConflictException {
    public BookCopiesConflict(String message) {
        super(message);
    }
}
