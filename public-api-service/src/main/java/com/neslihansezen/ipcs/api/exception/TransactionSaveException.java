package com.neslihansezen.ipcs.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransactionSaveException extends RuntimeException {
    public TransactionSaveException(String message) {
        super(message);
    }
}
