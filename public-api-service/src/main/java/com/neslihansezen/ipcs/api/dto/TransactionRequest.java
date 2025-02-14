package com.neslihansezen.ipcs.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {
    @NotNull(message = "{vehicle.type.not.valid}")
    @Pattern(regexp = "^[A-Za-z]{1,30}$", message = "{vehicle.type.not.valid}")
    private String vehicle;

    @Pattern(regexp = "^[0-9]{1,6}$", message = "{mileage.not.valid}")
    @NotNull(message = "{mileage.not.valid}")
    private String mileage;

    @Pattern(regexp = "^[0-9]{5}$", message = "{postcode.not.valid}")
    private String postcode;

}
