package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata;

import java.util.List;

public interface IGetITunesDataCase {
    List run(final String query);
}
