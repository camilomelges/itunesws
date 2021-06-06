package com.rafamilo.savedataapi.itunesdata.rabbitmq.consumers;

import com.rafamilo.savedataapi.configurations.RabbitContainerSingleton;
import com.rafamilo.savedataapi.configurations.RabbitMQTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {RabbitMQTest.class})
@SpringBootTest
class ITunesDataConsumerTest extends RabbitContainerSingleton {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void shouldBeSendMessage() throws InterruptedException {
        List<Map<String, String>> data = mountITunesApiResponse(20);
        rabbitTemplate.convertAndSend("iTunesSaveDataQueue", data);

        Awaitility.with().pollInterval(1, TimeUnit.MILLISECONDS).atMost(50, TimeUnit.SECONDS)
                .until(callable(receiveAndConvert("iTunesSaveDataQueue"), data), is(true));
    }

    private Callable<Object> callable(final List<Map<String, String>> target, final List<Map<String, String>> source) {
        return () -> target.equals(source);
    }

    private List<Map<String, String>> mountITunesApiResponse(final int quantity) {
        final List<Map<String, String>> iTunesResponse = new ArrayList<>();

        for (int i = 1; i < quantity + 1; i++) {
            final Map<String, String> data = new HashMap<>();
            for (int j = 1; j < 31; j++) {
                data.put(String.valueOf((i + j) * i), String.valueOf((i * j) * i));
            }
            iTunesResponse.add(data);
        }

        return iTunesResponse;
    }

    private List<Map<String, String>> receiveAndConvert(final String queueName) {
        return rabbitTemplate.receiveAndConvert(queueName, new ParameterizedTypeReference<List<Map<String, String>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
    }
}