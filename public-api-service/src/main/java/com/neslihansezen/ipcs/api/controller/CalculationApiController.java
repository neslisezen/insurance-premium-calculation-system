package com.neslihansezen.ipcs.api.controller;

import com.neslihansezen.ipcs.api.dto.BaseResponse;
import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import com.neslihansezen.ipcs.api.dto.TransactionResponse;
import com.neslihansezen.ipcs.api.entity.Transaction;
import com.neslihansezen.ipcs.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.neslihansezen.ipcs.api.constants.Messages.CALCULATION_SUCCESSFUL;


/**
 * Controller for handling transaction-related API endpoints.
 * Provides functionalities for saving transaction inputs and fetching all transactions.
 * This controller interacts with the {@link TransactionService} to save user inputs and fetch transactions.
 * <p>
 * Endpoint:
 * - POST /api/v1/public: Accepts a {@link TransactionService} object with details
 * of the vehicle, mileage, and postcode and returns a {@link BaseResponse} containing
 * the fetched premium, success state and message.
 * <p>
 * Dependencies:
 * - {@link TransactionService} for premium calculation logic.
 * - Static utility method {@code createBaseResponse} for creating a standardized response structure.
 * <p>
 * This is designed for external communication with the public API.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class CalculationApiController {
    private final TransactionService transactionService;

    /**
     * Endpoint for calculating an insurance premium based on provided parameters.
     * Accepts a request with vehicle type, mileage, and postcode, gets the premium from the external premium service,
     * and returns a success response containing the fetched premium value.
     *
     * @param request the request object containing details such as the vehicle type, mileage, and postcode used
     *                for premium calculation
     * @return ResponseEntity containing a BaseResponse with the fetched insurance premium as a Double,
     * success state, and a success message.
     */
    @PostMapping
    public ResponseEntity<BaseResponse<TransactionResponse>> getPremium(@Valid @RequestBody TransactionRequest request,
                                                                        @RequestHeader(value = "X-Source") String source) {
        TransactionResponse transactionResponse = transactionService.saveUserInput(request, source);
        return ResponseEntity.ok(createBaseResponse(transactionResponse));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    private BaseResponse<TransactionResponse> createBaseResponse(TransactionResponse response) {
        return BaseResponse.<TransactionResponse>builder()
                .success(true)
                .message(CALCULATION_SUCCESSFUL)
                .data(response)
                .build();
    }
}
