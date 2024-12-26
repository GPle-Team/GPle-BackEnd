package com.gple.backend.domain.post.common;

import com.gple.backend.domain.emoji.controller.dto.response.CheckEmojiResponse;
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

    public static boolean getMyReactedEmoji(List<Emoji> isClickedEmojis, EmojiType emojiType, Long id){
        return isClickedEmojis.stream().anyMatch((emoji -> emoji.getUser().getId().equals(id) && emoji.getEmojiType() == emojiType));
    }

    public static List<QueryPostResponse> postListToDto(List<Post> posts, Long userId){
        return posts.stream().map(post -> postToDto(post, userId)).toList();
    }

    public static QueryPostResponse postToDto(Post post, Long userId){
        List<Tag> tags = post.getTag();
        List<TagResponse> tagDtoList = tags.stream().map(tag -> TagResponse.builder()
            .id(tag.getUser().getId())
            .name(tag.getUser().getName())
            .build()
        ).toList();

        List<Emoji> emojis = post.getEmoji();
        EmojiResponse emojiDtoList = EmojiResponse.builder()
            .heartCount(getEmojiCount(emojis, EmojiType.HEART))
            .thumbsCount(getEmojiCount(emojis, EmojiType.CONGRATS))
            .thinkCount(getEmojiCount(emojis, EmojiType.THUMBSUP))
            .poopCount(getEmojiCount(emojis, EmojiType.THINKING))
            .chinaCount(getEmojiCount(emojis, EmojiType.POOP))
            .congCount(getEmojiCount(emojis, EmojiType.CHINA))
            .build();

        List<Boolean> checkEmoji = List.of(
            getMyReactedEmoji(emojis, EmojiType.HEART, userId),
            getMyReactedEmoji(emojis, EmojiType.CONGRATS, userId),
            getMyReactedEmoji(emojis, EmojiType.THUMBSUP, userId),
            getMyReactedEmoji(emojis, EmojiType.THINKING, userId),
            getMyReactedEmoji(emojis, EmojiType.POOP, userId),
            getMyReactedEmoji(emojis, EmojiType.CHINA, userId)
        );

        return QueryPostResponse.builder()
            .author(AuthorResponse.builder()
                .grade(post.getUser().getGrade())
                .name(post.getUser().getName())
                .id(post.getUser().getId())
                .build()
            )
            .checkEmoji(checkEmoji)
            .id(post.getId())
            .title(post.getTitle())
            .location(post.getLocation().getName())
            .tagList(tagDtoList)
            .createdTime(post.getCreatedTime())
            .emojiList(emojiDtoList)
            .imageUrl(post.getImageUrl())
            .build();
    }
}
