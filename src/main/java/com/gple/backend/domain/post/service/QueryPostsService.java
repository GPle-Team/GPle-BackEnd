package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gple.backend.domain.post.common.PostCommon.postListToDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPostsService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute() {
        List<Post> posts = postRepository.findAll();

        return postListToDto(posts);
    }
}
