package com.library.notification.notification.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "notification.exchange";
    public static final String QUEUE = "notification.queue";
    public static final String DLX = "notification.exchange.dlx";
    public static final String DLQ = "notification.queue.dlq";

    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(QUEUE)
                .deadLetterExchange(DLX)
                .deadLetterRoutingKey(DLQ)
                .build();
    }

    @Bean
    public Queue notificationDlq() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(eventsExchange())
                .with("notification.#");
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(notificationDlq())
                .to(deadLetterExchange())
                .with(DLQ);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var converter = new JacksonJsonMessageConverter();
        converter.setAlwaysConvertToInferredType(true);
        return converter;
    }
}