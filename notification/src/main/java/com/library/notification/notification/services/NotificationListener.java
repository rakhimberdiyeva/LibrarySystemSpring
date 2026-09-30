package com.library.notification.notification.services;

import com.library.notification.notification.configs.RabbitConfig;
import com.library.notification.notification.dto.NotificationCreate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onNotification(NotificationCreate notificationCreate) {
        log.info("Received {} for member {}", notificationCreate.getType(), notificationCreate.getMemberId());
        notificationService.create(notificationCreate);
    }
}