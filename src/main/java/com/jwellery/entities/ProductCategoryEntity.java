package com.jwellery.entities;

import com.jwellery.constant.ProductStatus;
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
public class ProductCategoryEntity extends BaseEntity {
    String name;
    String description;
    @Enumerated(EnumType.STRING)
    ProductStatus status;
    String imageUrl;
    @OneToMany(mappedBy = "productCategory",cascade = CascadeType.ALL)
    List<ProductTypeEntity> productTypes;
}
