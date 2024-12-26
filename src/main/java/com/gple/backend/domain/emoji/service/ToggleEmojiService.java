package com.gple.backend.domain.emoji.service;

import com.gple.backend.domain.emoji.controller.dto.request.EmojiRequest;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToggleEmojiService {
    private final EmojiRepository emojiRepository;
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Transactional
    public void execute(EmojiRequest emojiRequest) {
        User user = userUtil.getCurrentUser();
        Post post = postRepository.findById(emojiRequest.getPostId())
                .orElseThrow(() -> new HttpException(ExceptionEnum.NOT_FOUND_POST));

        Optional<Emoji> emoji = emojiRepository.findEmojiByUserIdAndEmojiTypeAndPostId(
            user.getId(),
            emojiRequest.getEmojiType(),
            post.getId()
        );

        if(emoji.isPresent()){
            emojiRepository.deleteById(emoji.get().getId());
        } else {
            Emoji createdEmoji = Emoji.builder()
                .id(0L)
                .post(post)
                .emojiType(emojiRequest.getEmojiType())
                .user(user)
                .build();
            emojiRepository.save(createdEmoji);
        }
    }
}
