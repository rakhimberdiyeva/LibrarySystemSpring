package com.library.notification.notification;

import com.library.notification.notification.dto.NotificationCreate;
import com.library.notification.notification.dto.NotificationResponse;
import com.library.notification.notification.exceptions.NotNotificationOwner;
import com.library.notification.notification.exceptions.NotificationNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);


    @Override
    public NotificationResponse create(NotificationCreate notificationCreate) {
        Notification notification = notificationRepository.save(new Notification(notificationCreate.getMemberId(), notificationCreate.getType(), notificationCreate.getMessage()));
        log.info("notification created {}", notification.getId());
        return notificationMapper.toDto(notification);
    }

    @Override
    public NotificationResponse getById(long id) {
        return notificationMapper.toDto(notificationRepository.findById(id).get());
    }

    @Override
    public List<NotificationResponse> getMemberNotifications(long memberId) {
        return notificationRepository.findByMemberId(memberId).stream().map(n -> notificationMapper.toDto(n)).toList();
    }

    @Override
    public NotificationResponse markAsRead(long memberId, long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFound("notification not found"));
        if (notification.getMemberId() != memberId){
            throw new NotNotificationOwner("can't access this notification");
        }
        return notificationMapper.toDto(notificationRepository.markAsRead(id));
    }
}
