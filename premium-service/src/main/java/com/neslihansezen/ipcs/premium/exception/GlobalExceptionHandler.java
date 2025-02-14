package com.neslihansezen.ipcs.premium.exception;

import com.neslihansezen.ipcs.premium.dto.BaseResponse;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Object> handleExternalServiceException(ExternalServiceException ex) {
        return buildResponseEntity(ex.getMessage());
    }

    /**
     * Handles FeignException thrown during Feign client calls.
     * Extracts the error message from the exception's response content and constructs
     * a custom error response using the message returned by the external service.
     *
     * @param ex the FeignException thrown during a Feign client call
     * @return a ResponseEntity object containing a custom error response
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException ex) {
        String responseBody = ex.contentUTF8();
        try {
            int messageStart = responseBody.indexOf("\"message\":\"") + 11;
            int messageEnd = responseBody.indexOf("\"", messageStart);
            return buildResponseEntity(responseBody.substring(messageStart, messageEnd));
        } catch (Exception e) {
            return buildResponseEntity(responseBody);
        }
    }

    private ResponseEntity<Object> buildResponseEntity(String message) {
        var response = BaseResponse.<Double>builder()
                .message(message)
                .success(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}

