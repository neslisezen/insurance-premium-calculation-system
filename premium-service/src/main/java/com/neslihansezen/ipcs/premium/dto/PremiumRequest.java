package com.neslihansezen.ipcs.premium.dto;

import lombok.Data;

@Data
public class PremiumRequest {
    private String vehicle;

    private String mileage;

    private String postcode;
}
