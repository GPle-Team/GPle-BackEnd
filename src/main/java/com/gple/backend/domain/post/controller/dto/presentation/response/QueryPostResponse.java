package com.gple.backend.domain.post.controller.dto.presentation.response;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResponse;
import com.gple.backend.domain.post.controller.dto.common.AuthorResponse;
import com.gple.backend.domain.tag.dto.response.TagResponse;
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
public class QueryPostResponse {
    private Long id;

    private String title;

    private List<String> imageUrl;

    private String location;

    private List<TagResponse> tagList;

    private AuthorResponse author;

    private EmojiResponse emojiList;

    private LocalDateTime createdTime;
}