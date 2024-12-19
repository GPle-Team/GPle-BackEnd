package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResponse;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.controller.dto.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.dto.response.TagResponse;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPostService {
    private final PostRepository postRepository;
    private final EmojiRepository emojiRepository;

    @Transactional(readOnly = true)
    public QueryPostResponse execute(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ExpectedException("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        List<TagResponse> tagResponse = post.getTag().stream().map(tag -> {
            User user = tag.getUser();
            return TagResponse.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .build();
        }).toList();

        List<Emoji> countEmoji = emojiRepository.findEmojiByPostId(post.getId());

        return QueryPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .location(post.getLocation())
                .imageUrl(post.getImageUrl())
                .tagList(tagResponse)
                .emojiList(EmojiResponse.builder()
                        .heartCount(getEmojiCount(countEmoji, EmojiType.HEART))
                        .congCount(getEmojiCount(countEmoji, EmojiType.CONGRATULATION))
                        .thumbsCount(getEmojiCount(countEmoji, EmojiType.THUMBSUP))
                        .thinkCount(getEmojiCount(countEmoji, EmojiType.THINKING))
                        .poopCount(getEmojiCount(countEmoji, EmojiType.POOP))
                        .chinaCount(getEmojiCount(countEmoji, EmojiType.CHINA))
                        .build()
                )
                .createdTime(post.getCreatedTime())
                .build();
    }

    public static Long getEmojiCount(List<Emoji> countEmojis, EmojiType emojiType) {
        return countEmojis.stream().filter(emoji -> emoji.getEmojiType() == emojiType).count();
    }
}
