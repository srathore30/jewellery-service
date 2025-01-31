package com.jewellery.dto.req.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProductImagesReq {
    List<String> oldImagesList;
    List<String> newImagesList;
}
