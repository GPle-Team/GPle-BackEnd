package com.gple.backend.domain.post.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPostResDto {
    private Long postId;

    private String title;

    private String imageUrl;

    private String location;

    private LocalDateTime createdTime;
}