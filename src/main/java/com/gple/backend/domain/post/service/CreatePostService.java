package com.gple.backend.domain.post.service;

import com.gple.backend.domain.post.controller.dto.presentation.request.CreatePostRequest;
import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.tag.entity.Tag;
import com.gple.backend.domain.tag.repository.TagRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserUtil userUtil;

    @Transactional
    public void execute(CreatePostRequest reqDto) {
        User currentUser = userUtil.getCurrentUser();
        Post post = Post.builder()
                .title(reqDto.getTitle())
                .location(reqDto.getLocation())
                .imageUrl(reqDto.getImageUrl())
                .createdTime(LocalDateTime.now())
                .user(currentUser)
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
