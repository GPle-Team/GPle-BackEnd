package com.gple.backend.domain.user.controller;

import com.gple.backend.domain.user.controller.dto.web.request.CreateUserProfileRequest;
import com.gple.backend.domain.user.controller.dto.GetUserInfoResponse;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<Void> createUserProfile(@Valid @RequestBody CreateUserProfileRequest request){
        userService.createUserProfile(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<User> getUserInfo(){
        return ResponseEntity.ok(userService.getUserInfo());
    }
}
