package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResponse;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.controller.dto.common.AuthorResponse;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.dto.response.TagResponse;
import com.gple.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gple.backend.domain.post.service.QueryPostService.getEmojiCount;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllPostService {
    private final PostRepository postRepository;
    private final EmojiRepository emojiRepository;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            List<TagResponse> tagResponse = post.getTag().stream().map(tag -> {
                User user = tag.getUser();
                return TagResponse.builder().userId(user.getId()).username(user.getName()).build();
            }).toList();

            List<Emoji> countEmoji = emojiRepository.findEmojiByPostId(post.getId());

            return QueryPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(AuthorResponse.builder()
                    .grade(post.getUser().getGrade())
                    .name(post.getUser().getName())
                    .id(post.getUser().getId())
                    .build()
                )
                .imageUrl(post.getImageUrl())
                .location(post.getLocation())
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
        }).toList();
    }
}
