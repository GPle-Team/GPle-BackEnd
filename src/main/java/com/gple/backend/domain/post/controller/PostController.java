package com.gple.backend.domain.post.controller;

import com.gple.backend.domain.post.controller.dto.presentation.request.CreatePostRequest;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
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

    @GetMapping("my")
    public ResponseEntity<List<QueryPostResponse>> getMyPost(){
        return ResponseEntity.ok(queryAllMyPostService.execute());
    }

    @GetMapping("react")
    public ResponseEntity<List<QueryPostResponse>> queryAllReactedPosts(){
        return ResponseEntity.ok(queryAllReactedPostService.execute());
    }
}