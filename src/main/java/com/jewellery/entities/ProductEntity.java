package com.jewellery.entities;

import com.jewellery.constant.InventoryStatus;
import com.jewellery.constant.Status;
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
    String itemCode;
    boolean isAvailable;
    @Enumerated(EnumType.STRING)
    Status status;
    @Enumerated(EnumType.STRING)
    InventoryStatus inventoryStatus;
    Float overAllRating;
    @Column(precision = 10)
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
    @OneToOne
    SpecificationEntity specificationEntity;
    ProductCategoryEntity productCategory;
}
