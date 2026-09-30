package com.library.loan.loan.services;

import com.library.loan.loan.dto.NotificationCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(NotificationCreate notificationCreate) {
        String routingKey = "notification." + notificationCreate.getType().name().toLowerCase();
        rabbitTemplate.convertAndSend("notification.exchange", routingKey, notificationCreate);
    }
}