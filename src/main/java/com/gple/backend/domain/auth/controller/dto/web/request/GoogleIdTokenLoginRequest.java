package com.gple.backend.domain.auth.controller.dto.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleIdTokenLoginRequest {
    private String idToken;
}
