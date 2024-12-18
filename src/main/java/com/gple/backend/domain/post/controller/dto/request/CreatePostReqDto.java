package com.gple.backend.domain.post.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostReqDto {
    @NotNull
    private String title;

    @NotNull
    private String location;

    @NotNull
    private List<Long> userList;

    @NotNull
    private String imageUrl;
}
