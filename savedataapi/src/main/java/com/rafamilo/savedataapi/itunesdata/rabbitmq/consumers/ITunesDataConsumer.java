package com.rafamilo.savedataapi.itunesdata.rabbitmq.consumers;

import com.rafamilo.savedataapi.itunesdata.repository.itunesdata.services.IITunesDataSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class ITunesDataConsumer {

    private static final String SW_PROVIDER_QUEUE_NAME = "iTunesSaveDataQueue";
    private final IITunesDataSaveService iiTunesDataSaveService;

    @RabbitListener(queues = SW_PROVIDER_QUEUE_NAME)
    public void listen(final List<Map<String, Object>> providers) {
        iiTunesDataSaveService.run(providers);
    }
}
