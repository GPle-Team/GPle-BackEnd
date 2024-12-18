package com.gple.backend.domain.emoji.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountEmojiDto {
    private String emojiType;

    private Long emojiCount;
}
