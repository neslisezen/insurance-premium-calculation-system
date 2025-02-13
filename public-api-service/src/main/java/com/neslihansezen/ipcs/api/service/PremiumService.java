package com.neslihansezen.ipcs.api.service;

import com.neslihansezen.ipcs.api.dto.BaseResponse;
import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import com.neslihansezen.ipcs.api.exception.ExternalServiceException;
import com.neslihansezen.ipcs.api.feign.PremiumClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
     * It communicates with the external premium service to fetch the premium amount.
     * If the external premium service responds with an error, an exception is thrown.
     *
     * @param request the transaction request containing necessary details such as
     *                vehicle type, mileage, and postcode for calculating the premium.
     * @return the calculated insurance premium as a double.
     * @throws ExternalServiceException if the external premium service fails or returns
     *                                  an error message.
     */
    public double calculate(TransactionRequest request) {
        BaseResponse<Double> premiumResponse = premiumClient.getInsurancePremium(request).getBody();
        if (!premiumResponse.isSuccess())
            throw new ExternalServiceException(premiumResponse.getMessage());
        return premiumResponse.getData();
    }
}
