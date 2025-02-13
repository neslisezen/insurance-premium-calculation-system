package com.neslihansezen.ipcs.api.service;

import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import com.neslihansezen.ipcs.api.dto.TransactionResponse;
import com.neslihansezen.ipcs.api.entity.Transaction;
import com.neslihansezen.ipcs.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class responsible for managing transactions related to insurance premium calculations.
 * It provides functionality to save user input as transactions and retrieve all transactions from the database.
 */
@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final PremiumService premiumService;

    /**
     * Saves the user input as a transaction in the database and returns a response containing
     * transaction details including the calculated insurance premium.
     * <p>
     * This method calculates the insurance premium based on the provided transaction request,
     * persists the transaction in the database, and builds a response object containing the
     * transaction details.
     *
     * @param request the transaction request containing details like vehicle type, mileage,
     *                and postcode required for premium calculation and transaction creation.
     * @param source  the source identifier indicating the origin of the request (e.g., internalApp, externalApp).
     * @return a {@code TransactionResponse} object containing the transaction ID and
     * calculated insurance premium.
     */
    @Transactional
    public TransactionResponse saveUserInput(TransactionRequest request, String source) {
        double insurancePremium = premiumService.calculate(request);
        return buildResponse(transactionRepository.save(new Transaction(request, insurancePremium, source)));
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    private TransactionResponse buildResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .insurancePremium(transaction.getInsurancePremium())
                .build();
    }
}
