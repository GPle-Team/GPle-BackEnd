package com.gple.backend.domain.auth.controller.dto.response;

import com.gple.backend.global.security.dto.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private TokenType tokenType;
    private String expires;
    private String token;
}
