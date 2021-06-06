package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.producers;

import com.rafamilo.itunesws.configurations.RabbitContainerSingleton;
import com.rafamilo.itunesws.configurations.RabbitMQTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {RabbitMQTest.class})
@SpringBootTest
class ITunesSaveDataProducerTest extends RabbitContainerSingleton {

    @Value("${spring.rabbitmq.queues.iTunesSaveDataQueue.name}")
    private String iTunesSaveDataQueueName;

    private String iTunesSaveDataQueueNameMock;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void beforeEach() {
        iTunesSaveDataQueueNameMock = iTunesSaveDataQueueName.concat("Mock");
    }

    @Test
    public <T> void shouldBeSendMessage() throws InterruptedException {
        List<T> iTunesData = new ArrayList<T>();

        rabbitTemplate.convertAndSend(iTunesSaveDataQueueNameMock, iTunesData);

        Awaitility.with().pollInterval(1, TimeUnit.MILLISECONDS).atMost(20, TimeUnit.SECONDS)
                .until(callable(receiveAndConvert(iTunesSaveDataQueueNameMock), iTunesData), is(true));
    }

    private <T> Callable<Object> callable(final List<T> target, final List<T> source) {
        return () -> target.equals(source);
    }

    private <T> List<T> receiveAndConvert(final String queueName) {
        return rabbitTemplate.receiveAndConvert(queueName, new ParameterizedTypeReference<List<T>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }
}