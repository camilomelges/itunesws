package com.rafamilo.savedataapi.itunesdata.repository.itunesdata.services;

import com.rafamilo.savedataapi.itunesdata.repository.itunesdata.ITunesData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class ITunesDataSaveService implements IITunesDataSaveService {

    private final MongoTemplate mongoTemplate;

    public void run(final List<Map<String, Object>> apiReturn) {
        for (final Map<String, Object> apiData : apiReturn) {
            final ITunesData iTunesData = new ITunesData();
            iTunesData.setTrackId(String.valueOf(apiData.get("trackId")));
            apiData.remove("trackId");

            try {
                final JSONObject data = new JSONObject();
                for (final Map.Entry<String, Object> entry : apiData.entrySet()) {
                    data.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
                iTunesData.setData(data);

                mongoTemplate.save(iTunesData);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
