package com.library.notification.notification.mappers;

import com.library.notification.notification.models.Notification;
import com.library.notification.notification.dto.NotificationResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponse toDto(Notification notification){
        NotificationResponse dto = new NotificationResponse();
        dto.setMemberId(notification.getMemberId());
        dto.setType(notification.getType());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        return dto;
    }
}
