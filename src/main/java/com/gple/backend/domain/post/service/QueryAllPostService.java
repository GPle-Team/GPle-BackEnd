package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.response.QueryPostResDto;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryAllPostService {
    private final PostRepository postRepository;

    @Transactional
    public List<QueryPostResDto> execute() {
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> new QueryPostResDto(
                post.getId(),
                post.getTitle(),
                post.getImageUrl(),
                post.getLocation(),
                post.getCreatedTime()
        )).toList();
    }
}
