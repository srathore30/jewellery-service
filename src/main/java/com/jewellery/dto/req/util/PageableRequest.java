package com.jewellery.dto.req.util;

import com.jewellery.constant.Constants;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PageableRequest {
    @Builder.Default
    @Range( min = 1, message = "page can not be zero or negative" )
    @Positive( message = "page can not be negative" )
    Integer page = 1;

    @Builder.Default
    @Positive( message = "size can not be negative" )
    Integer size = 20;

    @Builder.Default
    @Pattern(regexp = "DESC|ASC", flags = Pattern.Flag.CASE_INSENSITIVE)
    String sortOrder = Constants.DESCENDING;

    @Builder.Default
    String sortColumn = "createdTime";
}
