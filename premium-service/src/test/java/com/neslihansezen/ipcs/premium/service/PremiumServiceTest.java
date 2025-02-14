package com.neslihansezen.ipcs.premium.service;

import com.neslihansezen.ipcs.premium.util.CalculatorUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PremiumServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private PremiumService premiumService;

    @Test
    void shouldReturnCorrectPremium() {
        // Given
        String vehicleType = "lkw";
        String mileage = "15000";
        String postcode = "56075";

        double expectedRegionFactor = 6.50;
        double expectedVehicleFactor = 2.25;
        double expectedKmFactor = 1.0;

        when(dataService.getRegionFactor(postcode)).thenReturn(expectedRegionFactor);
        when(dataService.getVehicleFactor(vehicleType)).thenReturn(expectedVehicleFactor);
        Mockito.mockStatic(CalculatorUtil.class).when(() -> CalculatorUtil.calculateKmFactor(mileage)).thenReturn(expectedKmFactor);

        double expectedPremium = expectedRegionFactor * expectedVehicleFactor * expectedKmFactor;

        // When
        double actualPremium = premiumService.calculate(vehicleType, mileage, postcode);

        // Then
        assertEquals(expectedPremium, actualPremium);
    }
}