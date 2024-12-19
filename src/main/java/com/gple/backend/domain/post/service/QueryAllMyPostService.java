package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResponse;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.post.controller.dto.common.AuthorResponse;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.dto.response.TagResponse;
import com.gple.backend.domain.tag.entity.Tag;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gple.backend.domain.post.common.PostCommon.getEmojiCount;

@Service
@RequiredArgsConstructor
public class QueryAllMyPostService {
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute() {
        User user = userUtil.getCurrentUser();
        List<Post> posts = postRepository.findByUserId(user.getId());

        return posts.stream().map(post -> {
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
        }).toList();
    }
}
