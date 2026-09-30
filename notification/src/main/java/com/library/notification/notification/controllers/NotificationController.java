package com.library.notification.notification.controllers;

import com.library.notification.notification.dto.ApiResponse;
import com.library.notification.notification.dto.NotificationResponse;
import com.library.notification.notification.services.NotificationServiceImpl;
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
    public ApiResponse<List<NotificationResponse>> getUserNotifications(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId){
        return new ApiResponse<>(200, "success", notificationService.getMemberNotifications(userId));
    }

    @PutMapping("/{id}")
    public ApiResponse getUserNotifications(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId, @PathVariable long id){
         notificationService.markAsRead(userId, id);
         return new ApiResponse<>(200, "success", null);
    }

}
