package com.library.notification.notification.exceptions;

import com.library.notification.exceptions.ForbiddenException;

public class NotNotificationOwner extends ForbiddenException {
    public NotNotificationOwner(String message) {
        super(message);
    }
}
