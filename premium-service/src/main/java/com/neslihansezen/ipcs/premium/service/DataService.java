package com.neslihansezen.ipcs.premium.service;

import com.neslihansezen.ipcs.premium.dto.BaseResponse;
import com.neslihansezen.ipcs.premium.exception.ExternalServiceException;
import com.neslihansezen.ipcs.premium.feign.DataClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.neslihansezen.ipcs.premium.constant.Messages.NO_MESSAGE_AVAILABLE;


/**
 * Service class responsible for interacting with external data services to fetch
 * vehicle-specific and region-specific factors. This class uses caching to
 * optimize repeated requests for the same data.
 * <p>
 * Dependencies:
 * - {@link DataClient} to communicate with external data services.
 * - Spring's caching mechanism to store and retrieve previously fetched data efficiently.
 * <p>
 * Methods:
 * - {@code getVehicleFactor(String vehicleType)}:
 * Fetches the vehicle-specific factor from external service for a given vehicle type.
 * - {@code getRegionFactor(String postcode)}:
 * Fetches the region-specific factor from external service for a given postcode.
 * <p>
 * Exceptions:
 * - Throws {@link ExternalServiceException} if the response from the external service is
 * null or indicates a failure.
 * <p>
 * Caching:
 * - Results of {@code getVehicleFactor} are cached in the "vehicleFactor" cache.
 * - Results of {@code getRegionFactor} are cached in the "regionFactor" cache.
 */
@RequiredArgsConstructor
@Service
public class DataService {

    private final DataClient dataClient;

    /**
     * Retrieves the vehicle-specific factor for the given vehicle type from an external service.
     * The result is cached to optimize repeated data retrieval for the same vehicle type.
     *
     * @param vehicleType the type of the vehicle for which the factor is to be fetched
     * @return the vehicle-specific factor as a double value
     * @throws ExternalServiceException if the response from the external service is null
     *                                  or indicates a failure
     */
    @Cacheable("vehicleFactor")
    public double getVehicleFactor(String vehicleType) {
        ResponseEntity<BaseResponse<Double>> response = dataClient.getVehicleFactor(vehicleType);
        return validateAndExtractResponse(response);

    }

    /**
     * Retrieves the region-specific factor for the given postcode from an external service.
     * The result is cached to optimize repeated data retrieval for the same postcode.
     *
     * @param postcode the postcode for which the region factor is to be fetched
     * @return the region-specific factor as a double value
     * @throws ExternalServiceException if the response from the external service is null
     *                                  or indicates a failure
     */
    @Cacheable("regionFactor")
    public double getRegionFactor(String postcode) {
        ResponseEntity<BaseResponse<Double>> response = dataClient.getRegionFactor(postcode);
        return validateAndExtractResponse(response);

    }

    private double validateAndExtractResponse(ResponseEntity<BaseResponse<Double>> response) {
        BaseResponse<Double> baseResponse = response.getBody();
        if (baseResponse == null || !baseResponse.isSuccess()) {
            throw new ExternalServiceException(baseResponse != null ? baseResponse.getMessage() : NO_MESSAGE_AVAILABLE);
        }
        return baseResponse.getData();
    }

}

