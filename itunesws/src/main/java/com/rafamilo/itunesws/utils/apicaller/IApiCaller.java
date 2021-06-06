package com.rafamilo.itunesws.utils.apicaller;

import org.springframework.http.ResponseEntity;

public interface IApiCaller {
    <T> ResponseEntity<T> run(final String url, final Class<T> responseType);
}
