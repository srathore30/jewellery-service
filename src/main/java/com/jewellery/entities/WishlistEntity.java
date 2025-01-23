package com.jewellery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class WishlistEntity extends BaseEntity{
    @ManyToOne
    ProductEntity productEntity;
    @ManyToOne
    UserEntity userEntity;
    Integer quntity;
}
