package com.library.notification.notification;

import com.library.notification.notification.dto.NotificationCreate;
import com.library.notification.notification.dto.NotificationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
     NotificationResponse create(NotificationCreate notificationCreate);
     NotificationResponse getById(long id);
     List<NotificationResponse> getMemberNotifications(long memberId);
     void markAsRead(long memberId, long id);
}
