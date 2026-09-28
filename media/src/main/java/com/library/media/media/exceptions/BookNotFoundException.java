package com.library.media.media.exceptions;


import com.library.media.exceptions.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
