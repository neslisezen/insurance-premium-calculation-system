package com.neslihansezen.ipcs.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostcodeNotFoundException extends IllegalArgumentException {
    public PostcodeNotFoundException(String message) {
        super(message);
    }
}
