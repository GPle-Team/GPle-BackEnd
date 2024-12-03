package com.gple.backend.domain.post.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostReqDto {
    @NotBlank
    private String title;

    @NotBlank
    private String location;

    @NotBlank
    private String imageUrl;
}
