package com.jewellery.dto.req.product;

import com.jewellery.constant.InventoryStatus;
import com.jewellery.dto.req.specification.SpecificationReq;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReq {
    String title;
    String description;
    String modelName;
    boolean isAvailable;
    InventoryStatus inventoryStatus;
    Double sellingPrice;
    UpdateProductImagesReq updateProductImagesReq;
    Double originalPrice;
    String highlights;
    String keyFeatures;
    Long productTypeId;
    Long productCategoryId;
    SpecificationReq specificationReq;

}
