package com.library.loan.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationCreate {
    private long memberId;
    private NotificationType type;
    private String message;
}
