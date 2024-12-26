package com.gple.backend.domain.post.controller;

import com.gple.backend.domain.post.controller.dto.common.PostSortType;
import com.gple.backend.domain.post.controller.dto.common.PostType;
import com.gple.backend.domain.post.controller.dto.presentation.request.CreatePostRequest;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Location;
import com.gple.backend.domain.post.service.CreatePostService;
import com.gple.backend.domain.post.service.DeletePostService;
import com.gple.backend.domain.post.service.QueryPostByIdService;
import com.gple.backend.domain.post.service.QueryPostsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostService createPostService;
    private final DeletePostService deletePostService;
    private final QueryPostByIdService queryPostByIdService;
    private final QueryPostsService queryPostsService;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody @Valid CreatePostRequest reqDto) {
        createPostService.execute(reqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<QueryPostResponse> queryPost(@Valid @PathVariable Long postId) {
        QueryPostResponse post = queryPostByIdService.execute(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<QueryPostResponse>> queryAllPost(
        @RequestParam(required = false) PostSortType sort,
        @RequestParam(required = false) PostType type,
        @RequestParam(required = false) Location location
    ) {
        List<QueryPostResponse> posts = queryPostsService.execute(sort, type, location);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        deletePostService.execute(postId);
        return ResponseEntity.noContent().build();
    }
}