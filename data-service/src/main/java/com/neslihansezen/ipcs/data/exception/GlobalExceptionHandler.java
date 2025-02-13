package com.neslihansezen.ipcs.data.exception;

import com.neslihansezen.ipcs.data.dto.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {

        final var errorMessage =
                MessageFormat.format("No handler found for {0} {1}", ex.getHttpMethod(), ex.getRequestURL());
        return buildResponseEntity(errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String regex = "problem: *(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ex.getMessage());
        String problemMessage = ex.getMessage();
        if (matcher.find()) {
            problemMessage = matcher.group(1).trim();
        }
        return buildResponseEntity(problemMessage);
    }

    @ExceptionHandler({PostcodeNotFoundException.class, RegionNotFoundException.class, VehicleTypeNotFoundException.class})
    public ResponseEntity<Object> handlePostcodeNotFoundException(final IllegalArgumentException exception) {
        return buildResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(final Exception exception) {
        return buildResponseEntity(exception.getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(String message) {
        var response = BaseResponse.<Double>builder()
                .message(message)
                .isSuccess(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}

