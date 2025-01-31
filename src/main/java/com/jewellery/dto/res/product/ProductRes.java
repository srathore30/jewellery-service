package com.jewellery.dto.res.product;

import com.jewellery.constant.InventoryStatus;
import com.jewellery.constant.Status;
import com.jewellery.dto.res.productType.ProductTypeRes;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
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
    String  itemCode;
    boolean isAvailable;
    Status status;
    InventoryStatus inventoryStatus;
    Double sellingPrice;
    List<String> productImages;
    Double originalPrice;
    Date createdDate;
    String highlights;
    String keyFeatures;
    ProductTypeRes productType;
    ProductCategoryResponse productCategory;
}
