package com.rafamilo.savedataapi.itunesdata.repository.itunesdata.services;

import java.util.List;
import java.util.Map;

public interface IITunesDataSaveService {
    void run(final List<Map<String, Object>> apiReturn);
}
