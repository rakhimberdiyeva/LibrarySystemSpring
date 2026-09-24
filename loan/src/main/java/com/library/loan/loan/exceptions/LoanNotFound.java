package com.library.loan.loan.exceptions;

import com.library.loan.exceptions.NotFoundException;

public class LoanNotFound extends NotFoundException {
    public LoanNotFound(String message) {
        super(message);
    }
}
