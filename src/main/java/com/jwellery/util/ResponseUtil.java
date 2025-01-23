package com.jwellery.util;

import com.jwellery.constant.ApiErrorCodes;
import com.jwellery.utility_dto.res.ResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {

    public ResponseDto prepareSuccessResp(Object resp){
        return ResponseDto.builder().code(ApiErrorCodes.SUCCESS.getErrorCode())
                .message(ApiErrorCodes.SUCCESS.getErrorMessage())
                .data(resp).build();
    }
}