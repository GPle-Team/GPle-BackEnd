package com.gple.backend.domain.tag.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagResponse {
    private String username;

    private Long userId;
}