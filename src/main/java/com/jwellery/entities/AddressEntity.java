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
public class AddressEntity extends BaseEntity{
    String name;
    Long mobileNo;
    String landmark;
    String address;
    String pincode;
    String state;
    String city;
    String country;
    String tag;
    @ManyToOne
    UserEntity userEntity;
    Boolean isActive;

}
