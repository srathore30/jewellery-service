package com.jewellery.dto.req.productType;

import com.jewellery.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTypeReq {
    String name;
    String imageUrl;
    Long productCategoryId;
}
