package com.neslihansezen.ipcs.data.controller;

import com.neslihansezen.ipcs.data.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neslihansezen.ipcs.data.constants.Messages.REGION_FOUND_SUCCESSFULLY;
import static com.neslihansezen.ipcs.data.util.ResponseUtil.createBaseResponse;

/**
 * The RegionController class is a REST controller for handling HTTP requests related to region-based operations.
 *<p>
 * This controller provides an endpoint to retrieve the region factor based on a given postal code.
 * It interacts with the RegionService to process business logic and constructs the HTTP responses
 * with the retrieved data and corresponding messages.
 *<p>
 * Dependencies:
 * - RegionService: Provides the logic to fetch the region factor for a given postal code.
 *<p>
 * Mappings:
 * - Base endpoint: "/api/v1/regions"
 * - GET /factors/{postcode}: Retrieves the factor associated with the specified postal code.
 *<p>
 * This is designed for internal use within the microservices only.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/regions")
public class RegionController {

    private final RegionService regionService;

    /**
     * Retrieves the region factor associated with a specific postal code.
     *
     * @param postcode The postal code for which the region factor needs to be retrieved.
     * @return A ResponseEntity containing the region factor, success state, and a success message.
     */
    @GetMapping("/factors/{postcode}")
    public ResponseEntity<Object> getRegionFactor(@PathVariable String postcode) {
        double factor = regionService.getFactorByPostcode(postcode);
        return ResponseEntity.ok(createBaseResponse(factor, REGION_FOUND_SUCCESSFULLY));
    }
}
