package com.jwellery.entities;

import com.jwellery.constant.InventoryStatus;
import com.jwellery.constant.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class InventoryEntity extends BaseEntity {
    @OneToOne
    ProductEntity productEntity;
    Integer Stock;
    @Temporal(TemporalType.DATE)
    Date createdDate;
    @Enumerated(EnumType.STRING)
    InventoryStatus inventoryStatus;
    @Enumerated(EnumType.STRING)
    ProductStatus status;
}
