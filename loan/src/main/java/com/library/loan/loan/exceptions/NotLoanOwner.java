package com.library.loan.loan.exceptions;

import com.library.loan.exceptions.ForbiddenException;

public class NotLoanOwner extends ForbiddenException {
    public NotLoanOwner(String message) {
        super(message);
    }
}
