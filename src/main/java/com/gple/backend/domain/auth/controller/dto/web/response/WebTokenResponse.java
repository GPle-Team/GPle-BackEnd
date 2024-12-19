package com.gple.backend.domain.auth.controller.dto.web.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebTokenResponse {
    private String accessToken;
    private String accessExpiredAt;
    private String refreshToken;
    private String refreshExpiredAt;
}
