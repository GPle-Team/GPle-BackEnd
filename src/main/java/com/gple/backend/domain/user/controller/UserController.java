package com.gple.backend.domain.user.controller;

import com.gple.backend.domain.user.controller.dto.GetUserInfoResponse;
import com.gple.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetUserInfoResponse> getUserInfo(){
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
