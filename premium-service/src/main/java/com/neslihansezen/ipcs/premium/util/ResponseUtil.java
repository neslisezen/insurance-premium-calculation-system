package com.neslihansezen.ipcs.premium.util;

import com.neslihansezen.ipcs.premium.dto.BaseResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {
    public static BaseResponse<Double> createBaseResponse(Double response, String message) {
        return BaseResponse.<Double>builder()
                .success(true)
                .message(message)
                .data(response)
                .build();
    }
}
