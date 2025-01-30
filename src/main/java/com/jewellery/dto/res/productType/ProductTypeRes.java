package com.jewellery.dto.res.productType;

import com.jewellery.constant.Status;
import com.jewellery.dto.res.productcategory.ProductCategoryResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTypeRes {
    Long Id;
    String name;
    String imageUrl;
    Status status;
    ProductCategoryResponse productCategory;
}
