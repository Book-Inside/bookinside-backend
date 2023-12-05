package com.api.bookinside.domain.member.controller.dto;

import com.api.bookinside.jwt.JwtToken;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String tokenType;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(JwtToken jwtToken) {
        return LoginResponse.builder()
                .tokenType("Bearer")
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .build();
    }

}
