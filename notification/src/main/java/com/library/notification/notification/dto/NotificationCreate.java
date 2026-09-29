package com.library.notification.notification.dto;

import lombok.Data;

@Data
public class NotificationCreate {
    private long memberId;
    private NotificationType type;
    private String message;
}
