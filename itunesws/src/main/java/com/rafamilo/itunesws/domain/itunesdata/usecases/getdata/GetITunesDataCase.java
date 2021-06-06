package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata;

import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.producers.IITunesSaveDataProducer;
import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.services.IGetITunesDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class GetITunesDataCase implements IGetITunesDataCase {

    private final IGetITunesDataService getITunesDataService;

    private final IITunesSaveDataProducer iTunesSaveDataProducer;

    public List run(final String params) {
        final List data = getData(params);
        iTunesSaveDataProducer.run(data);
        return data;
    }

    private List getData(final String query) {
        return getITunesDataService.run(query);
    }
}
