package com.neslihansezen.ipcs.premium.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.neslihansezen.ipcs.premium.constant.Messages.NO_MESSAGE_AVAILABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private T data;

    @Builder.Default
    private String message = NO_MESSAGE_AVAILABLE;

    private boolean isSuccess;
}

