package com.rafamilo.itunesws.domain.itunesdata.entrypoint.controllers;

import com.rafamilo.itunesws.domain.itunesdata.usecases.getdata.IGetITunesDataCase;
import com.rafamilo.itunesws.utils.exceptions.BadRequest400Exception;
import com.rafamilo.itunesws.utils.exceptions.ErrorInternal500Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/itunes-data")
@RequiredArgsConstructor
public class ITunesDataController {

    private final IGetITunesDataCase getITunesDataCase;

    @GetMapping
    public List get(@RequestParam String query) {
        try {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
            validateQuery(query);
            return getITunesDataCase.run(query);
        } catch (Exception e) {
            if (e.getClass().getAnnotation(ResponseStatus.class) != null) {
                throw e;
            }
            throw new ErrorInternal500Exception(e.getMessage());
        }
    }

    private void validateQuery(final String query) {
        if (query == null || query.isEmpty() || query.isBlank()) {
            throw new BadRequest400Exception("Query cannot be null, empty or blank");
        }
    }
}
