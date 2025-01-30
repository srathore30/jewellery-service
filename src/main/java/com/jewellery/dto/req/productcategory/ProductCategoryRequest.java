package com.jewellery.dto.req.productcategory;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategoryRequest {


    String name;
    String description;
    String imageUrl;


}
