package com.jewellery.dto.res.productcategory;


import com.jewellery.dto.res.productType.ProductTypeRes;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategoryResponse {

    Long id;
    String name;
    String description;
    String imageUrl;
    List<ProductTypeRes> productTypes;
}
