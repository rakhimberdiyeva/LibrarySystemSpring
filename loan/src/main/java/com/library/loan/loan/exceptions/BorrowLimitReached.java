package com.library.loan.loan.exceptions;

import com.library.loan.exceptions.ConflictException;

public class BorrowLimitReached extends ConflictException {
    public BorrowLimitReached(String message) {
        super(message);
    }
}
