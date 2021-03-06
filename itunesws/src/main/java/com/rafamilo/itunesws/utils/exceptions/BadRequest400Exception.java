package com.rafamilo.itunesws.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequest400Exception extends RuntimeException {
    public BadRequest400Exception(String message) {
        super(message);
    }
}
