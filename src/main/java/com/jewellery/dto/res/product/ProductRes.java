package com.jewellery.dto.res.product;

import com.jewellery.constant.InventoryStatus;
import com.jewellery.constant.Status;
import com.jewellery.dto.res.productType.ProductTypeRes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRes {
    Long id;
    String title;
    String description;
    String modelName;
    Integer itemCode;
    Boolean isAvailable;
    Status status;
    InventoryStatus inventoryStatus;
   // Float overAllRating;
    Double sellingPrice;
    List<String> productImages;
    Double originalPrice;
    Date createdDate;
    String highlights;
    String keyFeatures;
    ProductTypeRes productTypeName;
    String productCategoryName;
}
