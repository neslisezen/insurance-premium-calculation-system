package com.neslihansezen.ipcs.premium.service;

import com.neslihansezen.ipcs.premium.dto.BaseResponse;
import com.neslihansezen.ipcs.premium.exception.ExternalServiceException;
import com.neslihansezen.ipcs.premium.feign.DataClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    @Mock
    private DataClient dataClient;

    @InjectMocks
    private DataService dataService;

    @Test
    void vehicleClientShouldReturnVehicleFactor_whenResponseIsValid() {
        // Given
        String vehicleType = "Cabrio";
        double expectedFactor = 1.5;
        BaseResponse<Double> baseResponse = new BaseResponse<>(expectedFactor, "vehicle type found successfully", true);
        ResponseEntity<BaseResponse<Double>> responseEntity = ResponseEntity.ok(baseResponse);
        // When
        when(dataClient.getVehicleFactor(vehicleType)).thenReturn(responseEntity);

        double result = dataService.getVehicleFactor(vehicleType);
        // Then
        Assertions.assertEquals(expectedFactor, result);
    }

    @Test
    void vehicleClientShouldThrowExternalServiceException_whenResponseIsNotSuccessful() {
        // Given
        String vehicleType = "Truck";

        BaseResponse<Double> baseResponse = new BaseResponse<>(null, "vehicle type not found", false);
        ResponseEntity<BaseResponse<Double>> responseEntity = ResponseEntity.ok(baseResponse);
        // When
        when(dataClient.getVehicleFactor(vehicleType)).thenReturn(responseEntity);
        // Then
        ExternalServiceException exception = Assertions.assertThrows(ExternalServiceException.class,
                () -> dataService.getVehicleFactor(vehicleType));

        Assertions.assertEquals("vehicle type not found", exception.getMessage());
    }

    @Test
    void vehicleClientShouldThrowExternalServiceException_whenResponseBodyIsNull() {
        // Given
        String vehicleType = "SUV";
        ResponseEntity<BaseResponse<Double>> responseEntity = ResponseEntity.ok(null);
        // When
        when(dataClient.getVehicleFactor(vehicleType)).thenReturn(responseEntity);
        // Then
        ExternalServiceException exception = Assertions.assertThrows(ExternalServiceException.class,
                () -> dataService.getVehicleFactor(vehicleType));

        Assertions.assertEquals("No message available", exception.getMessage());
    }

    @Test
    void regionClientShouldReturnRegionFactor_whenResponseIsValid() {
        // Given
        String regionType = "Berlin";
        double expectedFactor = 6.75;
        BaseResponse<Double> baseResponse = new BaseResponse<>(expectedFactor, "region found successfully", true);
        ResponseEntity<BaseResponse<Double>> responseEntity = ResponseEntity.ok(baseResponse);
        // When
        when(dataClient.getRegionFactor(regionType)).thenReturn(responseEntity);

        double result = dataService.getRegionFactor((regionType));
        // Then
        Assertions.assertEquals(expectedFactor, result);
    }

    @Test
    void regionClientShouldThrowExternalServiceException_whenResponseIsNotSuccessful() {
        // Given
        String regionType = "Koblenz";

        BaseResponse<Double> baseResponse = new BaseResponse<>(null, "region not found", false);
        ResponseEntity<BaseResponse<Double>> responseEntity = ResponseEntity.ok(baseResponse);
        // When
        when(dataClient.getRegionFactor(regionType)).thenReturn(responseEntity);
        // Then
        ExternalServiceException exception = Assertions.assertThrows(ExternalServiceException.class,
                () -> dataService.getRegionFactor(regionType));

        Assertions.assertEquals("region not found", exception.getMessage());
    }

    @Test
    void regionClientShouldThrowExternalServiceException_whenResponseBodyIsNull() {
        // Given
        String regionType = "Berlin";
        ResponseEntity<BaseResponse<Double>> responseEntity = ResponseEntity.ok(null);
        // When
        when(dataClient.getRegionFactor(regionType)).thenReturn(responseEntity);
        // Then
        ExternalServiceException exception = Assertions.assertThrows(ExternalServiceException.class,
                () -> dataService.getRegionFactor(regionType));

        Assertions.assertEquals("No message available", exception.getMessage());
    }
}