package com.jewellery.dto.res.User;

import com.jewellery.constant.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
    String token;
    String userName;
    String imageUrl;
    Status status;
}
