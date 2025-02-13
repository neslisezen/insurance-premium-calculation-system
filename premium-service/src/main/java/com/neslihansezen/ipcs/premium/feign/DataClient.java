package com.neslihansezen.ipcs.premium.feign;

import com.neslihansezen.ipcs.premium.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for communicating with the data-service to fetch
 * region-specific and vehicle-specific factors used in premium calculations.
 * <p>
 * The DataClient provides methods to interact with the data-service via
 * REST endpoints. This is used to retrieve factors associated with the region
 * and vehicle type.
 * <p>
 * Endpoint details:
 * - Base URL: http://localhost:8082
 * - "data-service": The name of the Feign client.
 * <p>
 * Methods:
 * - {@code getRegionFactor(String postcode)}:
 * Fetches region-specific factor based on the provided postcode.
 * - {@code getVehicleFactor(String vehicleType)}:
 * Fetches vehicle-specific factor based on the provided vehicle type.
 */
@FeignClient(name = "data-service", url = "${data.service.url}")
public interface DataClient {
    /**
     * Fetches the region-specific factor based on the provided postcode from the external service.
     *
     * @param postcode the postcode for which the region factor is required
     * @return a ResponseEntity containing a BaseResponse with the region-specific factor as a Double
     */
    @GetMapping("/regions/factors/{postcode}")
    ResponseEntity<BaseResponse<Double>> getRegionFactor(@PathVariable String postcode);

    /**
     * Fetches the vehicle-specific factor based on the provided vehicle type.
     *
     * @param vehicleType the type of the vehicle for which the factor is required
     * @return a ResponseEntity containing a BaseResponse with the vehicle-specific factor as a Double
     */
    @GetMapping("/vehicles/factors/{vehicleType}")
    ResponseEntity<BaseResponse<Double>> getVehicleFactor(@PathVariable String vehicleType);
}
