package com.gple.backend.domain.user.controller.dto.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private Long id;
    private Long grade;
    private String name;
    private String profileImage;
}
