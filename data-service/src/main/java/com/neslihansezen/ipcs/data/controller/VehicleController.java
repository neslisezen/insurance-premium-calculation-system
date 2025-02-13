package com.neslihansezen.ipcs.data.controller;

import com.neslihansezen.ipcs.data.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neslihansezen.ipcs.data.constants.Messages.VEHICLE_TYPE_FOUND_SUCCESSFULLY;
import static com.neslihansezen.ipcs.data.util.ResponseUtil.createBaseResponse;


/**
 * The VehicleController class is a REST controller for handling HTTP requests related to vehicle operations.
 * <p>
 * This controller provides an endpoint for retrieving the vehicle factor based on a given vehicle type.
 * The response includes the calculated factor and a success message.
 *<p>
 * Dependencies:
 * - VehicleService: Handles the business logic for fetching the vehicle factor.
 *<p>
 * Mappings:
 * - Base endpoint: "/api/v1/vehicles"
 * - GET /factors/{vehicleType}: Retrieves the factor associated with the specified vehicle type.
 *<p>
 * This is designed for internal use within the microservices only.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Retrieves the vehicle factor for a given vehicle type.
     *
     * @param vehicleType The type of vehicle for which the factor needs to be retrieved.
     * @return A ResponseEntity containing the calculated vehicle factor, success state and a success message.
     */
    @GetMapping("/factors/{vehicleType}")
    public ResponseEntity<Object> getVehicleFactor(@PathVariable String vehicleType) {
        double factor = vehicleService.getFactor(vehicleType);
        return ResponseEntity.ok(createBaseResponse(factor, VEHICLE_TYPE_FOUND_SUCCESSFULLY));
    }
}
