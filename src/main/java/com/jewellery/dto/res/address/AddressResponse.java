package com.jewellery.dto.res.address;

import com.jewellery.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponse {

    Long id;
    String name;
    Long mobileNo;
    String landmark;
    String address;
    String pincode;
    String state;
    String city;
    String country;
    String tag;
    Status status;
    Long userId;
    Boolean isActive;

}
