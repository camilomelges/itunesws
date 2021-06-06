package com.rafamilo.savedataapi.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RabbitMQTest extends RabbitMQ {

    @Value("${spring.rabbitmq.queues.iTunesSaveDataQueue.name}")
    private String iTunesSaveDataQueueName;

    @Bean
    public Queue iTunesSaveDataQueueMock() {
        return QueueBuilder.durable(iTunesSaveDataQueueName.concat("Mock")).build();
    }
}