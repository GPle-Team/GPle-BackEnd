package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.presentation.request.QueryPostsByLocationRequest;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gple.backend.domain.post.common.PostCommon.postListToDto;

@Service
@RequiredArgsConstructor
public class QueryPostsByLocationService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<QueryPostResponse> execute(QueryPostsByLocationRequest queryPostsByLocationRequest) {
        List<Post> posts = postRepository.findByLocation(queryPostsByLocationRequest.getLocation());
        return postListToDto(posts);
    }
}
