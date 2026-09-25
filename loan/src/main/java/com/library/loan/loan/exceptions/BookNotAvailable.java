package com.library.loan.loan.exceptions;

import com.library.loan.exceptions.ConflictException;
public class BookNotAvailable extends ConflictException {
    public BookNotAvailable(String message) {
        super(message);
    }
}
