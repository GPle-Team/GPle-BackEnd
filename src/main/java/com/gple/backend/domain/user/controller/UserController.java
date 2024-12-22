package com.gple.backend.domain.user.controller;

import com.gple.backend.domain.user.controller.dto.web.request.CreateUserProfileRequest;
import com.gple.backend.domain.user.controller.dto.web.response.GetUserResponse;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/profile")
    public ResponseEntity<Void> createUserProfile(@Valid @ModelAttribute CreateUserProfileRequest request){
        userService.createUserProfile(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<GetUserResponse> getProfile(){
        return ResponseEntity.ok(userService.getUserProfile());
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> getUserList(){
        return ResponseEntity.ok(userService.getUserList());
    }
}
