package com.jwellery.util;

import com.jwellery.constant.ApiErrorCodes;
import com.jwellery.util.dto.response.ResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {

    public ResponseDto prepareSuccessResp(Object resp){
        return ResponseDto.builder().code(ApiErrorCodes.SUCCESS.getErrorCode())
                .message(ApiErrorCodes.SUCCESS.getErrorMessage())
                .data(resp).build();
    }
}