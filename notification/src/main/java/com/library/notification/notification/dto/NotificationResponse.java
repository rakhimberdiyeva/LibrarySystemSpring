package com.library.notification.notification.dto;

import lombok.Data;

@Data
public class NotificationResponse {
    private long id;
    private long memberId;
    private NotificationType type;
    private String message;
    private boolean read;
}
