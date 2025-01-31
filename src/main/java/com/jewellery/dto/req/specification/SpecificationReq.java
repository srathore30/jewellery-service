package com.jewellery.dto.req.specification;

import com.jewellery.constant.DesignType;
import com.jewellery.constant.FinishType;
import com.jewellery.constant.StoneType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecificationReq {
    Long productId;
    String silverPurity;
    Double weight;
    StoneType stoneType;
    String size;
    FinishType finishType;
    boolean hallmark;
    DesignType designType;

}
