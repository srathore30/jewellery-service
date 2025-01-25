package com.jewellery.dto.res.wishlist;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistRes {
    Long Id;
    Long productId;
    String productName;
    Long userId;
    String userName;
    Integer quantity;
}
