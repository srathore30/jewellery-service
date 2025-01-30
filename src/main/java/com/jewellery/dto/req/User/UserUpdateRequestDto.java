package com.jewellery.dto.req.User;

import com.jewellery.constant.Constants;
import com.jewellery.constant.Role;
import com.jewellery.constant.Status;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.usertype.UserType;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequestDto {
    String name;
    String profilePhoto;

    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email, please enter valid email")
    String email;

    List<Role> userType;

    Status status;

    Long mobile;


}
