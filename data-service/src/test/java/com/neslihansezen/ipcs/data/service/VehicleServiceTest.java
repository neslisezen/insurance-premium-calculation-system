package com.neslihansezen.ipcs.data.service;

import com.neslihansezen.ipcs.data.entity.VehicleType;
import com.neslihansezen.ipcs.data.exception.VehicleTypeNotFoundException;
import com.neslihansezen.ipcs.data.repository.VehicleTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleTypeRepository vehicleTypeRepository;

    @Test
    @DisplayName("Should return vehicle factor when vehicle type exists")
    void shouldGetFactor_WhenVehicleTypeExists() {
        // Given
        String vehicleType = "PKW";
        double expectedFactor = 1.0;
        VehicleType vehicleTypeEntity = VehicleType.builder().shortName(vehicleType)
                .longName("Personalkraftwagen").factor(expectedFactor).build();
        // When
        Mockito.when(vehicleTypeRepository.findByShortNameOrLongName(vehicleType, vehicleType))
                .thenReturn(Optional.of(vehicleTypeEntity));

        double actualFactor = vehicleService.getFactor(vehicleType);

        // Then
        assertEquals(expectedFactor, actualFactor);
    }

    @Test
    @DisplayName("Should throw VehicleTypeNotFoundException when vehicle type does not exist")
    void getFactor_WhenVehicleTypeDoesNotExist() {
        // Given
        String vehicleType = "TRUCK";
        // When
        Mockito.when(vehicleTypeRepository.findByShortNameOrLongName("TRUCK", "TRUCK"))
                .thenReturn(Optional.empty());

        // Then
        assertThrows(VehicleTypeNotFoundException.class, () -> vehicleService.getFactor(vehicleType));
    }

    @Test
    @DisplayName("Should handle vehicle type with spaces and case insensitivity")
    void getFactor_WhenVehicleTypeHasSpacesAndDifferentCasing() {
        // Given
        String vehicleType = "  pKw ";
        String formattedVehicleType = "PKW";
        double expectedFactor = 1.0;
        VehicleType vehicleTypeEntity = VehicleType.builder().factor(expectedFactor).build();
        // When
        Mockito.when(vehicleTypeRepository.findByShortNameOrLongName(formattedVehicleType, formattedVehicleType))
                .thenReturn(Optional.of(vehicleTypeEntity));

        // Then
        double actualFactor = vehicleService.getFactor(vehicleType);

        assertEquals(expectedFactor, actualFactor);
        verify(vehicleTypeRepository, times(1)).findByShortNameOrLongName(formattedVehicleType, formattedVehicleType);
    }
}
