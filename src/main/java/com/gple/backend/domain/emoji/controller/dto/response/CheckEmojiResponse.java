package com.gple.backend.domain.emoji.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class CheckEmojiResponse {
    private boolean isHeart;

    private boolean isCong;

    private boolean isThumbs;

    private boolean isThink;

    private boolean isPoop;

    private boolean isChina;
}
