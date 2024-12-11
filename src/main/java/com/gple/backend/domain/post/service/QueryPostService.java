package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.response.QueryPostResDto;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryPostService {
    private final PostRepository postRepository;

    @Transactional
    public QueryPostResDto execute(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ExpectedException("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return QueryPostResDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .location(post.getLocation())
                .imageUrl(post.getImageUrl())
                .createdTime(post.getCreatedTime())
                .build();
    }
}
