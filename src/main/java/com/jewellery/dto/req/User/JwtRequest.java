package com.jewellery.dto.req.User;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtRequest {
    private Long mobile;
    private String password;
}
