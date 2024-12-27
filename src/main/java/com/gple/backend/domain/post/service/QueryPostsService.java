package com.gple.backend.domain.post.service;

import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.post.controller.dto.common.PostSortType;
import com.gple.backend.domain.post.controller.dto.common.PostType;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Location;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.service.UserService;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gple.backend.domain.post.common.PostCommon.postListToDto;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPostsService {
    private final PostRepository postRepository;
    private final EmojiRepository emojiRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute(
        PostSortType postSortType,
        PostType postType,
        Location location
    ) {
        User user = userUtil.getCurrentUser();

        List<Post> posts = switch (postType){
            case PostType.MY -> postRepository.findAllByUser(user);
            case PostType.REACTED -> emojiRepository.findByUser(user).stream().map(Emoji::getPost).distinct().toList();
            case null -> postRepository.findAll();
        };

        posts = posts.stream().sorted(
            switch (postSortType) {
                case POPULAR -> Comparator.comparingInt((Post a) -> a.getEmoji().size()).reversed();
                case CREATED -> Comparator.comparing(Post::getCreatedTime).reversed();
                case null -> Comparator.comparing(Post::getCreatedTime).reversed();
        }).toList();

        if(location != null){
            posts = posts.stream().filter(post -> post.getLocation() == location).toList();
        }

        return postListToDto(posts, user.getId());
    }
}
