package com.rafamilo.savedataapi.itunesdata.repository.itunesdata.services;

import com.rafamilo.savedataapi.configurations.MongoDBContainerSingleton;
import com.rafamilo.savedataapi.configurations.MongoDBTest;
import org.joor.Reflect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {MongoDBTest.class})
@SpringBootTest
class ITunesDataSaveServiceTest extends MongoDBContainerSingleton {

    @Autowired
    private ITunesDataSaveService iTunesDataSaveService;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void beforeEach() {
        Reflect.on(iTunesDataSaveService).set("mongoTemplate", mongoTemplate);
    }

    @Test
    public void shouldBeSaveAllDataWithoutException() throws JSONException {
        Assertions.assertDoesNotThrow(() -> {
            final int quantity = 100;
            iTunesDataSaveService.run(mountITunesApiResponse(quantity));
            Mockito.verify(mongoTemplate, Mockito.times(quantity)).save(Mockito.any());
        });
    }

    private List<Map<String, Object>> mountITunesApiResponse(final int quantity) {
        final List<Map<String, Object>> iTunesResponse = new ArrayList<>();

        for (int i = 1; i < quantity + 1; i++) {
            final Map<String, Object> data = new HashMap<>();
            for (int j = 1; j < 31; j++) {
                data.put(String.valueOf((i + j) * i), String.valueOf((i * j) * i));
            }
            iTunesResponse.add(data);
        }

        return iTunesResponse;
    }
}