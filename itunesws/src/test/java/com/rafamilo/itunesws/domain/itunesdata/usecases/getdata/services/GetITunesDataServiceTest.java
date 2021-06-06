package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.services;

import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.mappers.ITunesQueryBuilder;
import com.rafamilo.itunesws.utils.apicaller.ApiCaller;
import org.joor.Reflect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.Map;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class GetITunesDataServiceTest {

    @Value("${itunesws.properties.itunesApi.protocol}")
    private String protocol;

    @Value("${itunesws.properties.itunesApi.url}")
    private String url;

    @Autowired
    private GetITunesDataService getITunesDataService;

    @Mock
    private ApiCaller iApiCaller;

    private String getRequestURL() {
        return protocol.concat("://").concat(url).concat("/search?term=");
    }

    @Test
    public <T> void shouldBeCallIApiCallerWithCorrectParams() {
        Assertions.assertDoesNotThrow(() -> {
            final String query = "foo";
            final Class<?> responseType = Map.class;
            final ResponseEntity responseEntity = new ResponseEntity<>(new LinkedHashMap(), new HttpHeaders(), HttpStatus.OK);

            Mockito.when(iApiCaller.run(Mockito.anyString(), Mockito.any())).thenReturn(responseEntity);
            Reflect.on(getITunesDataService).set("apiCaller", iApiCaller);
            getITunesDataService.run(query);

            final String correctUrl = new StringBuilder()
                    .append(getRequestURL())
                    .append(ITunesQueryBuilder.run(query)).toString();

            Mockito.verify(iApiCaller, Mockito.times(1)).run(correctUrl, responseType);
        });
    }
}