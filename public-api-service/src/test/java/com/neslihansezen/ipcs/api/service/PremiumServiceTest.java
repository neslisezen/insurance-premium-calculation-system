package com.neslihansezen.ipcs.api.service;

import com.neslihansezen.ipcs.api.dto.BaseResponse;
import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import com.neslihansezen.ipcs.api.exception.ExternalServiceException;
import com.neslihansezen.ipcs.api.feign.PremiumClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PremiumServiceTest {

    @InjectMocks
    private PremiumService premiumService;

    @Mock
    private PremiumClient premiumClient;

    @Test
    void shouldReturnPremiumValue_WhenResponseIsSuccessful() {
        // Given
        TransactionRequest request = TransactionRequest.builder()
                .vehicle("pkw")
                .mileage("10000")
                .postcode("53227")
                .build();

        ResponseEntity<BaseResponse<Double>> mockResponse = ResponseEntity.ok(BaseResponse.<Double>builder()
                .success(true)
                .data(6.25)
                .build());
        // When
        when(premiumClient.getInsurancePremium(request)).thenReturn(mockResponse);
        double premium = premiumService.calculate(request);
        // Then
        assertThat(premium).isEqualTo(6.25);
    }

    @Test
    void calculate_ShouldThrowExternalServiceException_WhenResponseIsNotSuccessful() {
        // Given
        TransactionRequest request = TransactionRequest.builder()
                .vehicle("FLUGZEUG")
                .mileage("25000")
                .postcode("24539")
                .build();

        ResponseEntity<BaseResponse<Double>> mockResponse = ResponseEntity.badRequest().body(BaseResponse.<Double>builder()
                .success(false)
                .message("vehicle type not found: FLUGZEUG")
                .build());

        // When
        when(premiumClient.getInsurancePremium(request)).thenReturn(mockResponse);

        // Then
        assertThatThrownBy(() -> premiumService.calculate(request))
                .isInstanceOf(ExternalServiceException.class)
                .hasMessage("vehicle type not found: FLUGZEUG");
    }

    @Test
    void calculate_ShouldThrowExternalServiceException_WhenNoDataReturned() {
        // Given
        TransactionRequest request = TransactionRequest.builder()
                .vehicle("bus")
                .mileage("15000")
                .postcode("53227")
                .build();
        // When
        when(premiumClient.getInsurancePremium(any())).thenReturn(null);
        // Then
        assertThatThrownBy(() -> premiumService.calculate(request))
                .isInstanceOf(ExternalServiceException.class);
    }
}