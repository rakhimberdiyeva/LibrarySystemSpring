package com.library.loan.loan.exceptions;

import com.library.loan.exceptions.ConflictException;

public class BookAlreadyReturned extends ConflictException {
    public BookAlreadyReturned(String message) {
        super(message);
    }
}
