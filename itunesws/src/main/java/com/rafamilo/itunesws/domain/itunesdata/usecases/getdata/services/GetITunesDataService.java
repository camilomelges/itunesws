package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.services;

import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.mappers.ITunesQueryBuilder;
import com.rafamilo.itunesws.utils.apicaller.IApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class GetITunesDataService implements IGetITunesDataService {

    private final IApiCaller apiCaller;

    @Value("${itunesws.properties.itunesApi.protocol}")
    private String protocol;

    @Value("${itunesws.properties.itunesApi.url}")
    private String url;

    private String mountRequestUrl(final String query) {
        return new StringBuilder()
                .append(protocol)
                .append("://")
                .append(url)
                .append("/search?term=")
                .append(ITunesQueryBuilder.run(query)).toString();
    }

    public List run(final String query) {
        return (List) callApi(query).getBody().getOrDefault("results", new ArrayList<>());
    }

    private ResponseEntity<Map> callApi(final String query) {
        final String url = mountRequestUrl(query);

        log.info("Request iTunes api");
        return apiCaller.run(url, Map.class);
    }


}
