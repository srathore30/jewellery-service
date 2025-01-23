package com.jwellery.entities;

import com.jwellery.constant.ProductStatus;
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
public class ProductTypeEntity extends BaseEntity {
    String name;
    String imageUrl;
    @Enumerated(EnumType.STRING)
    Status status;
    @ManyToOne
    ProductCategoryEntity productCategory;
    @OneToMany(mappedBy = "productType")
    List<ProductEntity> productList;
}
