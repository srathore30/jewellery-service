package com.jewellery.dto.res.cart;

import com.jewellery.dto.res.product.ProductRes;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
   Long cartId;
   ProductRes productRes;
   Integer quantity;
   Double totalPrice;
}
