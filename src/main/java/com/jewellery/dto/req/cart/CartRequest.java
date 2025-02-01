package com.jewellery.dto.req.cart;

import com.jewellery.dto.req.specification.SpecificationReq;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    Long userId;
    Long productId;
    Integer quantity;
}
