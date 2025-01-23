package com.jewellery.entities;

import com.jewellery.constant.InventoryStatus;
import com.jewellery.constant.Status;
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
    Status status;
}
