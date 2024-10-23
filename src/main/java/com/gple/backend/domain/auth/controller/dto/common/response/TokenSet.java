package com.gple.backend.domain.auth.controller.dto.common.response;

import com.gple.backend.domain.auth.controller.dto.web.response.WebTokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenSet {
    private TokenResponse accessToken;
    private TokenResponse refreshToken;

    public WebTokenResponse toWebTokenResponse(){
        return WebTokenResponse.builder()
            .accessToken(accessToken.getToken())
            .accessExpires(accessToken.getExpires())
            .refreshToken(refreshToken.getToken())
            .refreshExpires(refreshToken.getExpires())
            .build();
    }
}
