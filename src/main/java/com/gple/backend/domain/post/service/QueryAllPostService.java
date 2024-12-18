package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.controller.dto.response.EmojiResDto;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.controller.dto.response.QueryPostResDto;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.dto.response.TagResDto;
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
    public List<QueryPostResDto> execute() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            List<TagResDto> tagResDto = post.getTag().stream().map(tag -> {
                User user = tag.getUser();
                return TagResDto.builder().userId(user.getId()).username(user.getUsername()).build();
            }).toList();

            List<Emoji> countEmoji = emojiRepository.findEmojiByPostId(post.getId());

            return new QueryPostResDto(
                    post.getId(),
                    post.getTitle(),
                    post.getImageUrl(),
                    post.getLocation(),
                    tagResDto,
                    EmojiResDto.builder()
                            .heartCount(getEmojiCount(countEmoji, EmojiType.HEART))
                            .congCount(getEmojiCount(countEmoji, EmojiType.CONGRATULATION))
                            .thumbsCount(getEmojiCount(countEmoji, EmojiType.THUMBSUP))
                            .thinkCount(getEmojiCount(countEmoji, EmojiType.THINKING))
                            .poopCount(getEmojiCount(countEmoji, EmojiType.POOP))
                            .chinaCount(getEmojiCount(countEmoji, EmojiType.CHINA))
                            .build(),
                    post.getCreatedTime()
            );
        }).toList();
    }
}
