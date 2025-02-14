package com.neslihansezen.ipcs.api.service;

import com.neslihansezen.ipcs.api.dto.BaseResponse;
import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import com.neslihansezen.ipcs.api.exception.ExternalServiceException;
import com.neslihansezen.ipcs.api.feign.PremiumClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.neslihansezen.ipcs.api.constants.Messages.EXTERNAL_CONNECTION_SERVICE_ERROR;

/**
 * Service class responsible for calculating the insurance premium by integrating with external services.
 * It handles the communication with the PremiumClient and processes the response to deliver the premium value.
 * If the external service fails, it throws an appropriate exception.
 */
@Service
@RequiredArgsConstructor
public class PremiumService {

    private final PremiumClient premiumClient;

    /**
     * Calculates the insurance premium based on the provided transaction request.
     * This method communicates with an external premium service to fetch the premium amount.
     *
     * @param request the transaction request containing details such as vehicle type, mileage,
     *                and postcode, which are required for calculating the insurance premium.
     * @return the calculated insurance premium as a double value.
     * @throws ExternalServiceException if the premium service fails or returns an invalid response.
     */
    public double calculate(TransactionRequest request) {
        ResponseEntity<BaseResponse<Double>> response = premiumClient.getInsurancePremium(request);
        if (response == null) {
            throw new ExternalServiceException(EXTERNAL_CONNECTION_SERVICE_ERROR);
        }
        return validateAndExtractResponse(response);
    }

    private double validateAndExtractResponse(ResponseEntity<BaseResponse<Double>> response) {
        BaseResponse<Double> baseResponse = response.getBody();
        if (baseResponse == null || !baseResponse.isSuccess()) {
            throw new ExternalServiceException(baseResponse != null ? baseResponse.getMessage() : EXTERNAL_CONNECTION_SERVICE_ERROR);
        }
        return baseResponse.getData();
    }
}
