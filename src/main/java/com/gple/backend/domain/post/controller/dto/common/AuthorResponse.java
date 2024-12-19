package com.gple.backend.domain.post.controller.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {
    private String name;
    private Long grade;
    private Long id;
}
