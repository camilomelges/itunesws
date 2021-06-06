package com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.mappers;

public abstract class ITunesQueryBuilder {

    public static String run(String query) {
        return query.replaceAll("[^a-zA-Z0-9]", "+").concat("&limit=200");
    }
}
