package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.request.CreatePostReqDto;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.entity.Tag;
import com.gple.backend.domain.tag.repository.TagRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void execute(CreatePostReqDto reqDto) {
        Post post = Post.builder()
                .title(reqDto.getTitle())
                .location(reqDto.getLocation())
                .imageUrl(reqDto.getImageUrl())
                .createdTime(LocalDateTime.now())
                .build();

        Post createdPost = postRepository.save(post);
        List<User> findUserList = userRepository.findAllById(reqDto.getUserList());

        List<Tag> tagList = findUserList.stream().map(user ->
            Tag.builder()
                    .id(0L)
                    .post(createdPost)
                    .user(user)
                    .build()
        ).toList();

        tagRepository.saveAll(tagList);
    }
}
