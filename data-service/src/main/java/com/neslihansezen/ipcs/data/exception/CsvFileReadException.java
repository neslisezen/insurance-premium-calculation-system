package com.neslihansezen.ipcs.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CsvFileReadException extends RuntimeException {
    public CsvFileReadException(String message) {
        super(message);
    }
}
