package com.library.loan.loan.exceptions;

public class NotLoanOwner extends RuntimeException {
    public NotLoanOwner(String message) {
        super(message);
    }
}
