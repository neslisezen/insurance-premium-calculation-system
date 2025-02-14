package com.neslihansezen.ipcs.data.service;

import com.neslihansezen.ipcs.data.entity.RegionMap;
import com.neslihansezen.ipcs.data.exception.PostcodeNotFoundException;
import com.neslihansezen.ipcs.data.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    @Test
    void shouldReturnFactor_whenValidPostcode() {
        // Given
        String validPostcode = "53227";
        double expectedFactor = 6.25;

        RegionMap regionMap = RegionMap.builder().postcode(validPostcode).regionFactor(expectedFactor).build();

        // When
        when(regionRepository.findRegionFactorByPostcode(validPostcode))
                .thenReturn(Optional.of(regionMap));

        double result = regionService.getFactorByPostcode(validPostcode);
        // Then
        assertThat(result).isEqualTo(expectedFactor);
        verify(regionRepository).findRegionFactorByPostcode(validPostcode);
    }

    @Test
    void shouldThrowPostcodeNotFoundException_whenInvalidPostcode() {
        // Given
        String invalidPostcode = "12345";
        // When
        when(regionRepository.findRegionFactorByPostcode(invalidPostcode))
                .thenReturn(Optional.empty());
        // Then
        assertThatThrownBy(() -> regionService.getFactorByPostcode(invalidPostcode))
                .isInstanceOf(PostcodeNotFoundException.class)
                .hasMessageContaining("postcode not found: " + invalidPostcode);

        verify(regionRepository).findRegionFactorByPostcode(invalidPostcode);
    }
}