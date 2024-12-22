package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gple.backend.domain.post.common.PostCommon.postToDto;

@Service
@RequiredArgsConstructor
public class QueryPostByIdService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public QueryPostResponse execute(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new HttpException(ExceptionEnum.NOT_FOUND_POST));

        return postToDto(post);
    }
}
