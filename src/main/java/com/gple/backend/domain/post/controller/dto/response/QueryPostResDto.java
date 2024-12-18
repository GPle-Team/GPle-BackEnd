package com.gple.backend.domain.post.controller.dto.response;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResDto;
import com.gple.backend.domain.tag.dto.response.TagResDto;
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

    private List<TagResDto> tagList;

    private EmojiResDto emojiList;

    private LocalDateTime createdTime;
}