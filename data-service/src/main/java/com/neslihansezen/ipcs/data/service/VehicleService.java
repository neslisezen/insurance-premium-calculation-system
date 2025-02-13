package com.neslihansezen.ipcs.data.service;

import com.neslihansezen.ipcs.data.entity.VehicleType;
import com.neslihansezen.ipcs.data.exception.VehicleTypeNotFoundException;
import com.neslihansezen.ipcs.data.repository.VehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.neslihansezen.ipcs.data.constants.Messages.VEHICLE_TYPE_NOT_FOUND;

/**
 * The VehicleService class handles operations related to vehicle types.
 * This service provides functionality to retrieve the vehicle factor based on a vehicle type.
 * It also caches the vehicle factor to optimize performance for frequent queries.
 *
 * @see VehicleTypeRepository
 * @see VehicleType
 * @see VehicleTypeNotFoundException
 */
@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleTypeRepository vehicleTypeRepository;

    /**
     * Retrieves the vehicle factor for the given vehicle type.
     *
     * This method checks if the vehicle factor is available for the provided vehicle type
     * and returns it. If the vehicle type is not found, a {@see VehicleTypeNotFoundException} is thrown.
     * The result is cached to improve performance for repeated queries with the same vehicle type.
     *
     * @param vehicleType
     * @return The vehicle factor for the given vehicle type.
     * @throws VehicleTypeNotFoundException If no vehicle type is found for the given vehicle type.
     * @see VehicleTypeRepository#findByShortNameOrLongName(String, String)
     */
    @Cacheable("vehicleFactor")
    public double getFactor(String vehicleType) {
        String vehicle = vehicleType.replaceAll("\\s", "").toUpperCase();

        return vehicleTypeRepository.findByShortNameOrLongName(vehicle, vehicle)
                .map(VehicleType::getFactor)
                .orElseThrow(() -> new VehicleTypeNotFoundException(VEHICLE_TYPE_NOT_FOUND + ": " + vehicleType));
    }
}

