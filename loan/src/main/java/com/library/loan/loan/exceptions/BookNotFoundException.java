package com.library.loan.loan.exceptions;

import com.library.loan.exceptions.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
