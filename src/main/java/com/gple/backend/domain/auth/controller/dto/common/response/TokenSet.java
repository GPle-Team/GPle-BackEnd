package com.gple.backend.domain.auth.controller.dto.common.response;

import com.gple.backend.domain.auth.controller.dto.web.response.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenSet {
    private TokenResponse accessToken;
    private TokenResponse refreshToken;

    public LoginResponse toWebTokenResponse(){
        return LoginResponse.builder()
            .accessToken(accessToken.getToken())
            .accessExpiredAt(accessToken.getExpires())
            .refreshToken(refreshToken.getToken())
            .refreshExpiredAt(refreshToken.getExpires())
            .build();
    }
}
