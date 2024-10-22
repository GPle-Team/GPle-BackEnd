package com.gple.backend.domain.auth.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOAuthToken {
    private String access_token;
    private Long expires_in;
    private String scope;
    private String token_type;
}
