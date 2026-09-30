package com.library.notification.notification.controllers;

import com.library.notification.notification.dto.ApiResponse;
import com.library.notification.notification.dto.NotificationResponse;
import com.library.notification.notification.services.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notification")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationService;

    @GetMapping("/all")
    public ApiResponse<List<NotificationResponse>> getUserNotifications(@AuthenticationPrincipal Jwt jwt){
        Long userId = Long.valueOf(jwt.getClaimAsString("userId"));
        return new ApiResponse<>(200, "success", notificationService.getMemberNotifications(userId));
    }

    @PutMapping("/{id}")
    public ApiResponse getUserNotifications(@AuthenticationPrincipal Jwt jwt, @PathVariable long id){
        Long userId = Long.valueOf(jwt.getClaimAsString("userId"));
        notificationService.markAsRead(userId, id);
         return new ApiResponse<>(200, "success", null);
    }

}
