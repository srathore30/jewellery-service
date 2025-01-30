package com.jewellery.dto.req.User;

import com.jewellery.constant.Role;
import com.jewellery.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    String name;

    String email;

    String password;

    List<Role> userType;

    Status status;

    Long mobile;

    String userPhotoUrl;
}
