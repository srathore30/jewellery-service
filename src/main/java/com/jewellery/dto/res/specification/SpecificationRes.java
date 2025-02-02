package com.jewellery.dto.res.specification;

import com.jewellery.constant.DesignType;
import com.jewellery.constant.FinishType;
import com.jewellery.constant.StoneType;
import com.jewellery.dto.res.product.ProductRes;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecificationRes {
    Long id;
    ProductRes productRes;
    String silverPurity;
    Double weight;
    StoneType stoneType;
    String size;
    FinishType finishType;
    boolean hallmark;
    DesignType designType;
}
