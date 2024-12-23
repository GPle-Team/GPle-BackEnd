package com.gple.backend.domain.post.controller;

import com.gple.backend.domain.post.controller.dto.presentation.request.CreatePostRequest;
import com.gple.backend.domain.post.controller.dto.presentation.request.QueryPostsByLocationRequest;
import com.gple.backend.domain.post.controller.dto.presentation.response.QueryPostResponse;
import com.gple.backend.domain.post.entity.Location;
import com.gple.backend.domain.post.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final QueryMyPostsService queryMyPostsService;
    private final QueryReactedPostsService queryReactedPostsService;
    private final QueryPostsByLocationService queryPostsByLocationService;

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
    public ResponseEntity<List<QueryPostResponse>> queryAllPost() {
        List<QueryPostResponse> posts = queryPostsService.execute();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("location")
    public ResponseEntity<List<QueryPostResponse>> getPostsByLocation(
        @RequestParam("type") Location location
    ){
           List<QueryPostResponse> posts = queryPostsByLocationService.execute(location);
           return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        deletePostService.execute(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("my")
    public ResponseEntity<List<QueryPostResponse>> getMyPost(){
        return ResponseEntity.ok(queryMyPostsService.execute());
    }

    @GetMapping("react")
    public ResponseEntity<List<QueryPostResponse>> queryAllReactedPosts(){
        return ResponseEntity.ok(queryReactedPostsService.execute());
    }
}