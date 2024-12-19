package com.gple.backend.domain.post.common;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResponse;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.post.controller.dto.common.AuthorResponse;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.tag.dto.response.TagResponse;
import com.gple.backend.domain.tag.entity.Tag;

import java.util.List;

public class PostCommon {
    public static Long getEmojiCount(List<Emoji> countEmojis, EmojiType emojiType) {
        return countEmojis.stream().filter(emoji -> emoji.getEmojiType() == emojiType).count();
    }

    public static List<QueryPostResponse> postListToDto(List<Post> posts){
        return posts.stream().map(PostCommon::postToDto).toList();
    }

    public static QueryPostResponse postToDto(Post post){
        List<Tag> tags = post.getTag();
        List<TagResponse> tagDtoList = tags.stream().map(tag -> TagResponse.builder()
            .id(tag.getUser().getId())
            .name(tag.getUser().getName())
            .build()
        ).toList();

        List<Emoji> emojis = post.getEmoji();
        EmojiResponse emojiDtoList = EmojiResponse.builder()
            .heartCount(getEmojiCount(emojis, EmojiType.HEART))
            .thumbsCount(getEmojiCount(emojis, EmojiType.THUMBSUP))
            .thinkCount(getEmojiCount(emojis, EmojiType.THINKING))
            .poopCount(getEmojiCount(emojis, EmojiType.POOP))
            .chinaCount(getEmojiCount(emojis, EmojiType.CHINA))
            .congCount(getEmojiCount(emojis, EmojiType.CONGRATULATION))
            .build();

        return QueryPostResponse.builder()
            .author(AuthorResponse.builder()
                .grade(post.getUser().getGrade())
                .name(post.getUser().getName())
                .id(post.getUser().getId())
                .build()
            )
            .id(post.getId())
            .title(post.getTitle())
            .location(post.getLocation())
            .tagList(tagDtoList)
            .createdTime(post.getCreatedTime())
            .emojiList(emojiDtoList)
            .imageUrl(post.getImageUrl())
            .build();
    }
}
