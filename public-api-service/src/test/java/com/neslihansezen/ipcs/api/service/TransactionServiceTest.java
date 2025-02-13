//package com.neslihansezen.ipcs.api.service;
//
//import com.neslihansezen.ipcs.api.dto.TransactionRequest;
//import com.neslihansezen.ipcs.api.dto.TransactionResponse;
//import com.neslihansezen.ipcs.api.entity.Transaction;
//import com.neslihansezen.ipcs.api.repository.TransactionRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class TransactionServiceTest {
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private PremiumService premiumService;
//
//    @InjectMocks
//    private TransactionService transactionService;
//
//    public TransactionServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSaveUserInputWithValidData() {
//        TransactionRequest request = TransactionRequest.builder()
//                .vehicle("Car")
//                .mileage("12345")
//                .postcode("56075")
//                .build();
//        String source = "web";
//        double mockPremium = 9.75;
//        Transaction mockTransaction = new Transaction(request, mockPremium, source);
//        mockTransaction.setId(1L);
//
//        when(premiumService.calculate(request)).thenReturn(mockPremium);
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);
//
//        TransactionResponse response = transactionService.saveUserInput(request, source);
//
//        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
//        verify(transactionRepository, times(1)).save(transactionCaptor.capture());
//        verify(premiumService, times(1)).calculate(request);
//
//        Transaction capturedTransaction = transactionCaptor.getValue();
//        assertEquals(mockPremium, capturedTransaction.getInsurancePremium());
//        assertEquals(source, capturedTransaction.getSource());
//        assertNotNull(response);
//        assertEquals(mockTransaction.getId(), response.getId());
//        assertEquals(mockPremium, response.getInsurancePremium());
//    }
//
//    @Test
//    public void testSaveUserInputWithNullPostcode() {
//        TransactionRequest request = TransactionRequest.builder()
//                .vehicle("suv")
//                .mileage("3000")
//                .postcode(null)
//                .build();
//        String source = "mobile";
//        double mockPremium = 150.0;
//        Transaction mockTransaction = new Transaction(request, mockPremium, source);
//        mockTransaction.setId(2L);
//
//        when(premiumService.calculate(request)).thenReturn(mockPremium);
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);
//
//        TransactionResponse response = transactionService.saveUserInput(request, source);
//
//        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
//        verify(transactionRepository, times(1)).save(transactionCaptor.capture());
//        verify(premiumService, times(1)).calculate(request);
//
//        Transaction capturedTransaction = transactionCaptor.getValue();
//        assertEquals(mockPremium, capturedTransaction.getInsurancePremium());
//        assertEquals(source, capturedTransaction.getSource());
//        assertNotNull(response);
//        assertEquals(mockTransaction.getId(), response.getId());
//        assertEquals(mockPremium, response.getInsurancePremium());
//    }
//
//    @Test
//    public void testSaveUserInputWithInvalidMileage() {
//        TransactionRequest request = TransactionRequest.builder()
//                .vehicle("MOTORRAD")
//                .mileage("9999999")
//                .postcode("79241")
//                .build();
//        String source = "api";
//        double mockPremium = 500.0;
//        Transaction mockTransaction = new Transaction(request, mockPremium, source);
//        mockTransaction.setId(3L);
//
//        when(premiumService.calculate(request)).thenReturn(mockPremium);
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);
//
//        TransactionResponse response = transactionService.saveUserInput(request, source);
//
//        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
//        verify(transactionRepository, times(1)).save(transactionCaptor.capture());
//        verify(premiumService, times(1)).calculate(request);
//
//        Transaction capturedTransaction = transactionCaptor.getValue();
//        assertEquals(mockPremium, capturedTransaction.getInsurancePremium());
//        assertEquals(source, capturedTransaction.getSource());
//        assertNotNull(response);
//        assertEquals(mockTransaction.getId(), response.getId());
//        assertEquals(mockPremium, response.getInsurancePremium());
//    }
//}