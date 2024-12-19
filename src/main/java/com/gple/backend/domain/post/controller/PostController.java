package com.gple.backend.domain.post.controller;

import com.gple.backend.domain.post.controller.dto.request.CreatePostRequest;
import com.gple.backend.domain.post.controller.dto.response.QueryReactedPostListResponse;
import com.gple.backend.domain.post.controller.dto.response.QueryAllMyPostResponse;
import com.gple.backend.domain.post.controller.dto.response.QueryPostResponse;
import com.gple.backend.domain.post.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostService createPostService;
    private final DeletePostService deletePostService;
    private final QueryPostService queryPostService;
    private final QueryAllPostService queryAllPostService;
    private final QueryAllMyPostService queryAllMyPostService;
    private final QueryAllReactedPostService queryAllReactedPostService;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody @Valid CreatePostRequest reqDto) {
        createPostService.execute(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<QueryPostResponse> queryPost(@Valid @PathVariable Long postId) {
        QueryPostResponse post = queryPostService.execute(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<QueryPostResponse>> queryAllPost() {
        List<QueryPostResponse> postLists = queryAllPostService.execute();
        return ResponseEntity.ok(postLists);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        deletePostService.execute(postId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 내가 올린 게시물을 불러온다.
    */
    @GetMapping("my")
    public ResponseEntity<List<QueryAllMyPostResponse>> getMyPost(){
        return ResponseEntity.ok(queryAllMyPostService.execute());
    }

    @GetMapping("react")
    public ResponseEntity<List<QueryReactedPostListResponse>> queryAllReactedPosts(){
        return ResponseEntity.ok(queryAllReactedPostService.execute());
    }
}

// 내가 올린 게시물과 내가 반응을 누른 게시물