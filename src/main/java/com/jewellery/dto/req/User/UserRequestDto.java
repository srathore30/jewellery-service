package com.jewellery.dto.req.User;

import com.jewellery.constant.Constants;
import com.jewellery.constant.Role;
import com.jewellery.constant.Status;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email, please enter valid email")
    String email;

    String password;

    List<Role> userType;

    Long mobile;

    String userPhotoUrl;
}
