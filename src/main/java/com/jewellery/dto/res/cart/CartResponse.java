package com.jewellery.dto.res.cart;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
   Long cartId;
   String productName;
   Double productPrice;
   Integer quantity;
   Double totalPrice;
}
