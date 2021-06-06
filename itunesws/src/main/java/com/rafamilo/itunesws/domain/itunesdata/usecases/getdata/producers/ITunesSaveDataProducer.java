package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.producers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ITunesSaveDataProducer implements IITunesSaveDataProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queues.iTunesSaveDataQueue.name}")
    private String iTunesSaveDataQueueName;

    public void run(final List data) {
        CompletableFuture.runAsync(() -> rabbitTemplate.convertAndSend(iTunesSaveDataQueueName, data));
    }
}
