package com.jewellery.dto.req.wishlist;

import com.jewellery.dto.req.specification.SpecificationReq;
import com.jewellery.entities.ProductEntity;
import com.jewellery.entities.UserEntity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistReq {

    Long productId;
    Long userId;
    Integer quantity;
}
