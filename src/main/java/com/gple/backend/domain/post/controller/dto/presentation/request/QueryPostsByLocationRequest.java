package com.gple.backend.domain.post.controller.dto.presentation.request;

import com.gple.backend.domain.post.entity.Location;
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
public class QueryPostsByLocationRequest {
    @NotNull
    private Location location;
}
