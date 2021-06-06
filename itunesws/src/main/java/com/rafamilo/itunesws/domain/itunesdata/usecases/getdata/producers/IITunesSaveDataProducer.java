package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.producers;

import java.util.List;

public interface IITunesSaveDataProducer {
    void run(final List data);
}
