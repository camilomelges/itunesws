package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata;

import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.producers.ITunesSaveDataProducer;
import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.services.IGetITunesDataService;
import org.joor.Reflect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class GetITunesDataCaseTest {

    @Autowired
    private GetITunesDataCase getITunesDataCase;

    @Mock
    private IGetITunesDataService getITunesDataService;

    @Mock
    private ITunesSaveDataProducer iTunesSaveDataProducer;

    @BeforeEach
    void beforeEach() {
        Reflect.on(getITunesDataCase).set("getITunesDataService", getITunesDataService);

        Mockito.doNothing().when(iTunesSaveDataProducer).run(Mockito.any());
        Reflect.on(getITunesDataCase).set("iTunesSaveDataProducer", iTunesSaveDataProducer);
    }

    @Test
    public void shouldBeCallIApiCallerWithCorrectParams() {
        final String query = "foo";

        Assertions.assertDoesNotThrow(() -> {
            getITunesDataCase.run(query);

            Mockito.verify(getITunesDataService, Mockito.times(1)).run(query);
        });
    }

    @Test
    public void shouldCallProducerWhenValidateIsTrue() {
        final String query = "foo";

        Assertions.assertDoesNotThrow(() -> {
            getITunesDataCase.run(query);

            Mockito.verify(iTunesSaveDataProducer, Mockito.times(1)).run(Mockito.any());
        });
    }
}