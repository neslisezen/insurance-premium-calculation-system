package com.neslihansezen.ipcs.premium.service;

import com.neslihansezen.ipcs.premium.util.CalculatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for calculating insurance premiums based on various factors.
 * This class utilizes external data services and utility functions to factor in region,
 * vehicle type, and mileage.
 *<p>
 * Dependencies:
 * - {@link DataService} to fetch region and vehicle-specific factors from external services.
 * - {@link CalculatorUtil} to compute mileage-based factors.
 *<p>
 * Methods:
 * - {@code calculate(String vehicleType, String mileage, String postcode)}:
 *   Calculates the premium by multiplying the region factor, vehicle factor, and mileage factor.
 *<p>
 * Calculation Workflow:
 * 1. Fetches the region factor using the provided postcode.
 * 2. Fetches the vehicle factor using the given vehicle type.
 * 3. Determines the mileage factor based on the mileage value.
 * 4. Returns the product of the above three factors as the calculated premium value.
 */
@RequiredArgsConstructor
@Service
public class PremiumService {

    private final DataService dataService;

    /**
     * Calculates the insurance premium based on vehicle type, mileage, and postcode.
     * Data obtained from external services and utility computations.
     *
     * @param vehicleType the type of the vehicle (e.g., bus, suv, pkw) used to calculate the vehicle factor
     * @param mileage the mileage of the vehicle as a string, used to determine the mileage factor
     * @param postcode the geographical postcode, used to fetch the region-specific factor
     * @return the calculated insurance premium as a double, derived by multiplying the region factor, vehicle factor,
     * and mileage factor
     */
    public double calculate(String vehicleType, String mileage, String postcode) {
        double regionFactor = dataService.getRegionFactor(postcode);
        double vehicleFactor = dataService.getVehicleFactor(vehicleType);
        double kmFactor = CalculatorUtil.calculateKmFactor(mileage);
        return regionFactor * vehicleFactor * kmFactor;
    }
}
