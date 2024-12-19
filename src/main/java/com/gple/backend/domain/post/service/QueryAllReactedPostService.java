package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResDto;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.controller.dto.response.QueryMyAllPostResDto;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.dto.response.TagResDto;
import com.gple.backend.domain.tag.entity.Tag;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllEmotionPostService {
    private final PostRepository postRepository;
    private final UserUtil userUtil;
    private final EmojiRepository emojiRepository;

    @Transactional(readOnly = true)
    public List<QueryMyAllPostResDto> execute() {
        User user = userUtil.getCurrentUser();
        List<Emoji> myDistinctEmotion = emojiRepository.findDistinctByUserId(user.getId());
        List<Post> posts = postRepository.findByUserId(user.getId());

        return posts.stream().map(post -> {
            List<Tag> tags = post.getTag();
            List<TagResDto> tagDtoList = tags.stream().map(tag -> TagResDto.builder()
                .userId(tag.getUser().getId())
                .username(tag.getUser().getUsername())
                .build()
            ).toList();

            List<Emoji> emojis = post.getEmoji();
            EmojiResDto emojiDtoList = EmojiResDto.builder()
                .heartCount(getEmojiCount(emojis, EmojiType.HEART))
                .thumbsCount(getEmojiCount(emojis, EmojiType.THUMBSUP))
                .thinkCount(getEmojiCount(emojis, EmojiType.THINKING))
                .poopCount(getEmojiCount(emojis, EmojiType.POOP))
                .chinaCount(getEmojiCount(emojis, EmojiType.CHINA))
                .congCount(getEmojiCount(emojis, EmojiType.CONGRATULATION))
                .build();

            return QueryMyAllPostResDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .location(post.getLocation())
                .tagList(tagDtoList)
                .createdTime(post.getCreatedTime())
                .emojiList(emojiDtoList)
                .imageUrl(post.getImageUrl())
                .build();
        }).toList();
    }

    public static Long getEmojiCount(List<Emoji> countEmojis, EmojiType emojiType) {
        return countEmojis.stream().filter(emoji -> emoji.getEmojiType() == emojiType).count();
    }
}
