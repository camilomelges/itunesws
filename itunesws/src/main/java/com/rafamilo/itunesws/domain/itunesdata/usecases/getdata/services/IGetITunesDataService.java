package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.services;

import java.util.List;

public interface IGetITunesDataService {
    List run(final String query);
}
