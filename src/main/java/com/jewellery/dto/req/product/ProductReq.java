package com.jewellery.dto.req.product;

import com.jewellery.constant.InventoryStatus;
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
    Integer itemCode;
    boolean isAvailable;
    InventoryStatus inventoryStatus;
    //Float overAllRating;
    Double sellingPrice;
    List<String> productImages;
    Double originalPrice;
    String highlights;
    String keyFeatures;
    Long productTypeId;
    Long productCategoryId;

}
