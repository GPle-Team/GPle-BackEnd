package com.gple.backend.domain.emoji.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmojiResDto {
    private Long heartCount;

    private Long congCount;

    private Long thumbsCount;

    private Long thinkCount;

    private Long poopCount;

    private Long chinaCount;
}
