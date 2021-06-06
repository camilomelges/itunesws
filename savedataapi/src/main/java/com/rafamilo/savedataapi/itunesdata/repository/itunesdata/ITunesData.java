package com.rafamilo.savedataapi.itunesdata.repository.itunesdata;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "itunesdata")
public class ITunesData implements Serializable {

    @Indexed(unique = true)
    private String trackId;

    private JSONObject data;
}
