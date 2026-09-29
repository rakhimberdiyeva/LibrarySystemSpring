package com.library.notification.notification;

import com.library.notification.notification.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notification")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationService;

    @GetMapping("/all")
    public List<NotificationResponse> getUserNotifications(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId){
        return notificationService.getMemberNotifications(userId);
    }

    @PutMapping("/{id}")
    public NotificationResponse getUserNotifications(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId, @PathVariable long id){
        return notificationService.markAsRead(userId, id);
    }

}
