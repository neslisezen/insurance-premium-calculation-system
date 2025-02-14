package com.neslihansezen.ipcs.api.exception;

import com.neslihansezen.ipcs.api.dto.BaseResponse;
import com.neslihansezen.ipcs.api.dto.TransactionResponse;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.neslihansezen.ipcs.api.constants.Messages.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles `MethodArgumentNotValidException` exceptions, which are thrown when argument
     * validation fails during a request. It extracts the first validation error message and
     * constructs a custom error response with that message.
     *
     * @param ex      the `MethodArgumentNotValidException` containing validation error details
     * @param headers the HTTP headers to be written to the response
     * @param status  the HTTP status code to be written to the response
     * @param request the current `WebRequest` that triggered the exception
     * @return a `ResponseEntity` containing a custom error response with the validation error message
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage() != null
                        ? fieldError.getDefaultMessage()
                        : UNKNOWN_VALIDATION_ERROR
                )
                .findFirst()
                .orElse(VALIDATION_ERROR);

        return buildResponseEntity(errorMessage);
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
        try
        {
            int messageStart = responseBody.indexOf("\"message\":\"") + 11;
            int messageEnd = responseBody.indexOf("\"", messageStart);
            return buildResponseEntity(responseBody.substring(messageStart, messageEnd));
        }
        catch (Exception e)
        {
            return buildResponseEntity(responseBody);
        }
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Object> handleExternalServiceException(final ExternalServiceException exception) {
        return buildResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(TransactionSaveException.class)
    public ResponseEntity<Object> handleTransactionSaveException(final TransactionSaveException exception) {
        return buildResponseEntity(TRANSACTION_SAVE_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(String message) {
        var response = BaseResponse.<TransactionResponse>builder()
                .message(message)
                .success(false)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
