package com.rafamilo.itunesws.utils.apicaller;

import com.rafamilo.itunesws.utils.exceptions.ErrorInternal500Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class ApiCaller implements IApiCaller {
    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> run(final String url, final Class<T> responseType) {
        try {
            final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType);

            if (!response.getStatusCode().isError()) {
                return response;
            }

            throw new ErrorInternal500Exception(Objects.requireNonNull(response.getBody()).toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ErrorInternal500Exception(e.getMessage());
        }
    }
}
