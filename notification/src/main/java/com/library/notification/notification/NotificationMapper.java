package com.library.notification.notification;

import com.library.notification.notification.dto.NotificationResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    NotificationResponse toDto(Notification notification){
        NotificationResponse dto = new NotificationResponse();
        dto.setMemberId(notification.getMemberId());
        dto.setType(notification.getType());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        return dto;
    }
}
