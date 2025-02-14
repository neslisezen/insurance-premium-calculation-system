package com.neslihansezen.ipcs.api.service;

import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import com.neslihansezen.ipcs.api.dto.TransactionResponse;
import com.neslihansezen.ipcs.api.entity.Transaction;
import com.neslihansezen.ipcs.api.exception.ExternalServiceException;
import com.neslihansezen.ipcs.api.exception.TransactionSaveException;
import com.neslihansezen.ipcs.api.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.neslihansezen.ipcs.api.constants.Messages.TRANSACTION_SAVE_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PremiumService premiumService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void shouldThrowExceptionWithSpecificErrorMessageWhenCalculationFails() {
        // Given
        TransactionRequest request = TransactionRequest.builder()
                .vehicle("BUS")
                .mileage("1000")
                .postcode("12345")
                .build();
        String source = "internalApp";
        // When
        when(premiumService.calculate(request)).thenThrow(new ExternalServiceException(("postcode not found")));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () ->
                transactionService.saveUserInput(request, source)
        );
        // Then
        assertEquals("postcode not found", exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    public void shouldThrowExceptionWhenTransactionSaveFails() {
        // Given
        TransactionRequest request = TransactionRequest.builder()
                .vehicle("PKW")
                .mileage("10000")
                .postcode("53227")
                .build();
        String source = "externalApp";
        double mockPremium = 6.25;
        // When
        when(premiumService.calculate(request)).thenReturn(mockPremium);
        when(transactionRepository.save(any(Transaction.class))).thenThrow(new TransactionSaveException(TRANSACTION_SAVE_ERROR));

        TransactionSaveException exception = assertThrows(TransactionSaveException.class, () ->
                transactionService.saveUserInput(request, source)
        );
        // Then
        assertEquals(TRANSACTION_SAVE_ERROR, exception.getMessage());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void shouldSaveUserInputWithValidData() {
        // Given
        TransactionRequest request = TransactionRequest.builder()
                .vehicle("pkW")
                .mileage("10000")
                .postcode("53227")
                .build();
        String source = "externalApp";
        double mockPremium = 6.25;
        Transaction mockTransaction = new Transaction(request, mockPremium, source);
        TransactionResponse mockResponse = TransactionResponse.builder()
                .id(mockTransaction.getId())
                .insurancePremium(mockPremium)
                .build();
        // When
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        when(premiumService.calculate(request)).thenReturn(mockPremium);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);
        TransactionResponse response = transactionService.saveUserInput(request, source);

        // Then
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(premiumService, times(1)).calculate(request);
        verify(transactionRepository).save(transactionCaptor.capture());

        assertNotNull(response);
        assertEquals(mockTransaction.getId(), response.getId());
        assertEquals(mockPremium, response.getInsurancePremium());

        Transaction capturedTransaction = transactionCaptor.getValue();
        assertEquals("pkW", capturedTransaction.getVehicle());
        assertEquals(10000, capturedTransaction.getMileage());
        assertEquals("53227", capturedTransaction.getPostcode());
        assertEquals(source, capturedTransaction.getSource());
        assertEquals(mockPremium, capturedTransaction.getInsurancePremium());

    }
}