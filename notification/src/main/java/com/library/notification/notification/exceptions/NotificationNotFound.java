package com.library.notification.notification.exceptions;

import com.library.notification.exceptions.NotFoundException;

public class NotificationNotFound extends NotFoundException {
    public NotificationNotFound(String message) {
        super(message);
    }
}
