package com.gple.backend.domain.auth.controller;

import com.gple.backend.domain.auth.controller.dto.web.response.RefreshTokenResponse;
import com.gple.backend.domain.auth.controller.dto.web.response.WebTokenResponse;
import com.gple.backend.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/google/login")
    public ResponseEntity<WebTokenResponse> joinUser(@RequestParam(name = "code") String code){
        return ResponseEntity.ok(authService.googleLogin(code));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @RequestHeader(name = "Refresh-Token", required = false)
        String refreshToken
    ){
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestHeader("Refresh-Token") String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}