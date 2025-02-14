package com.neslihansezen.ipcs.premium.controller;

import com.neslihansezen.ipcs.premium.dto.BaseResponse;
import com.neslihansezen.ipcs.premium.dto.PremiumRequest;
import com.neslihansezen.ipcs.premium.service.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.neslihansezen.ipcs.premium.constant.Messages.PREMIUM_CALCULATED_SUCCESSFULLY;
import static com.neslihansezen.ipcs.premium.util.ResponseUtil.createBaseResponse;

/**
 * REST controller that provides endpoint(s) for calculating insurance premiums.
 * This controller interacts with the {@link PremiumService} to process the given input
 * and calculate the insurance premium based on vehicle type, mileage, and postcode.
 *<p>
 * Endpoint:
 * - POST /api/premiums/calculate: Accepts a {@link PremiumRequest} object with details
 *   of the vehicle, mileage, and postcode and returns a {@link BaseResponse} containing
 *   the calculated premium and success message.
 *<p>
 * Dependencies:
 * - {@link PremiumService} for premium calculation logic.
 * - Static utility method {@code createBaseResponse} for creating a standardized response structure.
 *<p>
 * This is designed for internal communication within the microservices only.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/premiums")
public class PremiumController {

    private final PremiumService premiumService;

    /**
     * Endpoint for calculating an insurance premium based on provided parameters.
     * Accepts a request with vehicle type, mileage, and postcode, calculates the premium,
     * and returns a success response containing the computed premium value.
     *
     * @param request the request object containing details such as the vehicle type, mileage, and postcode used
     * for premium calculation
     * @return ResponseEntity containing a BaseResponse with the calculated insurance premium as a Double,
     * success state, and a success message.
     */
    @PostMapping("/calculate")
    public ResponseEntity<BaseResponse<Double>> getInsurancePremium(@RequestBody PremiumRequest request) {
        double premium = premiumService.calculate(request.getVehicle(), request.getMileage(), request.getPostcode());
        return ResponseEntity.ok(createBaseResponse(premium, PREMIUM_CALCULATED_SUCCESSFULLY));
    }
}
