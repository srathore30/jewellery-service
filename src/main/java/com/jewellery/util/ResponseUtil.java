package com.jewellery.util;

import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.dto.res.util.ResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResponseUtil {

    public ResponseDto prepareSuccessResp(Object resp){
        return ResponseDto.builder().code(ApiErrorCodes.SUCCESS.getErrorCode())
                .message(ApiErrorCodes.SUCCESS.getErrorMessage())
                .data(resp).build();
    }
}