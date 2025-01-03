package com.gple.backend.domain.auth.controller.dto.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String accessExpiredAt;
    private String refreshToken;
    private String refreshExpiredAt;
}
