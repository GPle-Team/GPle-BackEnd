package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gple.backend.domain.post.common.PostCommon.postListToDto;

@Service
@RequiredArgsConstructor
public class QueryReactedPostsService {
    private final UserUtil userUtil;
    private final EmojiRepository emojiRepository;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute() {
        User user = userUtil.getCurrentUser();
        List<Emoji> reactedPostEmojis = emojiRepository.findByUserId(user.getId());

        List<Post> reactedPosts = reactedPostEmojis.stream()
            .map(Emoji::getPost)
            .distinct()
            .toList();

        return postListToDto(reactedPosts);
    }
}
