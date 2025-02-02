package com.jewellery.dto.res.wishlist;

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
public class WishlistRes {
    Long Id;
    ProductRes product;
    Long userId;
    String userName;
    Integer quantity;
    ProductRes productRes;
    JwtResponse user;
}
