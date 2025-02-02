package com.jwellery.entities;

import com.jwellery.constant.InventoryStatus;
import com.jwellery.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ProductEntity extends BaseEntity{
    String title;
    @Column(length = 500)
    String description;
    String modelName;
    @Column(unique = true)
    Integer itemCode;
    Boolean isAvailable;
    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;
    @Enumerated(EnumType.STRING)
    InventoryStatus inventoryStatus;
    Float overAllRating;
    Double sellingPrice;
    List<String> productImages;
    Double originalPrice;
    @Temporal(TemporalType.DATE)
    Date createdDate;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TagsEntity> tags;
    @Column(length = 400)
    String highlights;
    @Column(length = 400)
    String keyFeatures;
    @ManyToOne
    ProductTypeEntity productType;
//    @OneToOne(cascade = CascadeType.ALL)
//    SpecificationEntity specificationEntity;
    ProductCategoryEntity productCategory;
}
