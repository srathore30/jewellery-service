package com.jewellery.dto.res.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
}