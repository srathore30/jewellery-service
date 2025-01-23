package com.jwellery.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jwellery.constant.ProductStatus;
import com.jwellery.constant.Role;
import com.jwellery.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserEntity extends BaseEntity {
    String name;
    String email;
    @Column(unique = true)
    Long mobileNo;
    String gender;
    String imageUrl;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    @Enumerated(EnumType.STRING)
    Status status;
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL, orphanRemoval = true)
    List<AddressEntity> addressList;
    @OneToMany(mappedBy = "userEntity")
    List<OrderItemEntity> orderItemList;
    @OneToMany(mappedBy = "userEntity")
    List<WishlistEntity> wishListItemList;
    @OneToMany(mappedBy = "userEntity")
    List<CartEntity> cartItemList;

}
