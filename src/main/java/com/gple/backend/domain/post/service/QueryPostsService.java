package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.service.UserService;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gple.backend.domain.post.common.PostCommon.postListToDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPostsService {
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute() {
        User user = userUtil.getCurrentUser();
        List<Post> posts = postRepository.findAll();

        return postListToDto(posts, user.getId());
    }
}
