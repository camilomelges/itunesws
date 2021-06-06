package com.rafamilo.itunesws.utils.apicaller;

import com.rafamilo.itunesws.utils.exceptions.ErrorInternal500Exception;
import org.joor.Reflect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class ApiCallerTest {

    @Autowired
    private ApiCaller apiCaller;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public <T> void shouldThrowsInternalServeErrorWhenResponseIsError() {
        Assertions.assertThrows(ErrorInternal500Exception.class, () -> {
            final String url = "http://foobar.com";
            final Class<?> responseType = ArrayList.class;
            final ResponseEntity responseEntity = new ResponseEntity<>("", new HttpHeaders(), HttpStatus.BAD_REQUEST);

            Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), (Class<Object>) Mockito.any())).thenReturn(responseEntity);
            Reflect.on(apiCaller).set("restTemplate", restTemplate);
            apiCaller.run(url, responseType);
        });
    }

    @Test
    public <T> void shouldThrowsInternalServeErrorWhenErrorOccurred() {
        Assertions.assertThrows(ErrorInternal500Exception.class, () -> {
            final String url = "http://foobar.com";
            final Class<?> responseType = ArrayList.class;
            final ResponseEntity responseEntity = new ResponseEntity<>("", new HttpHeaders(), HttpStatus.BAD_REQUEST);

            Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), (Class<Object>) Mockito.any())).thenThrow(new NullPointerException());
            Reflect.on(apiCaller).set("restTemplate", restTemplate);
            apiCaller.run(url, responseType);
        });
    }

    @Test
    public <T> void shouldBeCallIApiCallerWithCorrectParams() {
        Assertions.assertDoesNotThrow(() -> {
            final String url = "http://foobar.com";
            final Class<?> responseType = ArrayList.class;
            final ResponseEntity responseEntity = new ResponseEntity<>("", new HttpHeaders(), HttpStatus.OK);

            Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), (Class<Object>) Mockito.any())).thenReturn(responseEntity);
            Reflect.on(apiCaller).set("restTemplate", restTemplate);
            apiCaller.run(url, responseType);
        });
    }
}