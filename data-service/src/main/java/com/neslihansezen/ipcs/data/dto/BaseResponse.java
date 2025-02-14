package com.neslihansezen.ipcs.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.neslihansezen.ipcs.data.constants.Messages.NO_MESSAGE_AVAILABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private T data;

    @Builder.Default
    private String message = NO_MESSAGE_AVAILABLE;

    private boolean success;
}
