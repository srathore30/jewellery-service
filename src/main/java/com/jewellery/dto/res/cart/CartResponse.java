package com.jewellery.dto.res.cart;

import com.jewellery.dto.res.User.JwtResponse;
import com.jewellery.dto.res.product.ProductRes;
import com.jewellery.dto.res.specification.SpecificationRes;
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
   JwtResponse user;
}
