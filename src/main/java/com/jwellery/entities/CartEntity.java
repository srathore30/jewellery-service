package com.jwellery.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class CartEntity extends BaseEntity{
    @ManyToOne
    ProductEntity productEntity;
    @ManyToOne
    UserEntity userEntity;
    Integer quantity;
}
