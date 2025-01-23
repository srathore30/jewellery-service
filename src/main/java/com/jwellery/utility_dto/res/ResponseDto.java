package com.jwellery.utility_dto.res;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ResponseDto<T> {
    int code;
    String message;
    T data;
}
