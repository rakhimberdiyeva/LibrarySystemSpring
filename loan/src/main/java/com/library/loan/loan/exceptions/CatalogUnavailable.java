package com.library.loan.loan.exceptions;

public class CatalogUnavailable extends RuntimeException {
    public CatalogUnavailable(String message) {
        super(message);
    }
}
