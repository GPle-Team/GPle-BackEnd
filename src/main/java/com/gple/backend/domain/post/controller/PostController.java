package com.gple.backend.domain.post.controller;

import com.gple.backend.domain.post.controller.dto.request.CreatePostReqDto;
import com.gple.backend.domain.post.controller.dto.response.QueryPostResDto;
import com.gple.backend.domain.post.service.CreatePostService;
import com.gple.backend.domain.post.service.DeletePostService;
import com.gple.backend.domain.post.service.QueryAllPostService;
import com.gple.backend.domain.post.service.QueryPostService;
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

    @PostMapping("/")
    public ResponseEntity<Void> post(@RequestBody @Valid CreatePostReqDto reqDto) {
        createPostService.execute(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<QueryPostResDto> queryPost(@Valid @PathVariable Long postId) {
        QueryPostResDto post = queryPostService.execute(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/")
    public ResponseEntity<List<QueryPostResDto>> queryAllPost() {
        List<QueryPostResDto> postLists = queryAllPostService.execute();
        return ResponseEntity.ok(postLists);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        deletePostService.execute(postId);
        return ResponseEntity.noContent().build();
    }
}
