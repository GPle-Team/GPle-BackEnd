package com.gple.backend.domain.emoji.controller.dto.request;

import com.gple.backend.domain.emoji.entity.EmojiType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmojiReqDto {
    @NotNull
    private Long postId;

    @NotNull
    private EmojiType emojiType;
}
