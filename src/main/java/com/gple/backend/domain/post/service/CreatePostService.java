package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.request.CreatePostReqDto;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreatePostService {
    private final PostRepository postRepository;

    @Transactional
    public void execute(CreatePostReqDto reqDto) {
        Post post = Post.builder()
                .title(reqDto.getTitle())
                .location(reqDto.getLocation())
                .imageUrl(reqDto.getImageUrl())
                .createdTime(LocalDateTime.now())
                .build();

        postRepository.save(post);
    }
}
