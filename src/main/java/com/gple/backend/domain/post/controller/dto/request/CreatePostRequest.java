package com.gple.backend.domain.post.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    @NotNull
    private String title;

    @NotNull
    private String location;

    private List<Long> userList;

    @NotNull
    @Size(max = 3)
    private List<String> imageUrl;
}
