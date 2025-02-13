package com.neslihansezen.ipcs.api.feign;

import com.neslihansezen.ipcs.api.dto.BaseResponse;
import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client interface to communicate with the external premium service.
 * This client is responsible for sending requests to calculate the insurance premium and
 * receiving the response from the external service.
 * <p>
 * Endpoint details:
 * - Base URL: http://localhost:8081/api/v1/premiums
 * - "premium-service": The name of the Feign client.
 */
@FeignClient(name = "premium-service", url = "${premium.service.url}")
public interface PremiumClient {
    /**
     * Calculates the insurance premium based on the provided transaction details.
     * This method communicates with an external premium service to fetch the premium amount.
     *
     * @param request the transaction request containing details such as vehicle type, mileage,
     *                and postcode, which are required for calculating the insurance premium.
     * @return a ResponseEntity containing a BaseResponse object with the calculated insurance
     * premium as a Double.
     */
    @PostMapping("/calculate")
    ResponseEntity<BaseResponse<Double>> getInsurancePremium(@RequestBody TransactionRequest request);
}
