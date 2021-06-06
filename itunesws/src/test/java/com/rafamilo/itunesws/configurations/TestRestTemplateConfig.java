package com.rafamilo.itunesws.configurations;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestConfiguration
public class TestRestTemplateConfig {

    @Bean("testRestTemplate")
    public RestTemplate testRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(mountMessageConverters());

        return restTemplate;
    }

    private List<HttpMessageConverter<?>> mountMessageConverters() {
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);

        return messageConverters;
    }
}
