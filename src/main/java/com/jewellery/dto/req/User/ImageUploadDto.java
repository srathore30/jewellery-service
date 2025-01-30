package com.jewellery.dto.req.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageUploadDto {
    String userPhotoUrl;
    Long userId;
}
