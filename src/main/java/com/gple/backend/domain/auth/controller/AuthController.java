package com.gple.backend.domain.auth.controller;

import com.gple.backend.domain.auth.controller.dto.web.request.GoogleIdTokenLoginRequest;
import com.gple.backend.domain.auth.controller.dto.web.response.RefreshTokenResponse;
import com.gple.backend.domain.auth.controller.dto.web.response.LoginResponse;
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
    public ResponseEntity<LoginResponse> joinUser(@RequestParam(name = "code") String code){
        return ResponseEntity.ok(authService.googleLogin(code));
    }

    @PostMapping("/google/token")
    public ResponseEntity<LoginResponse> loginByIdToken(@RequestBody GoogleIdTokenLoginRequest googleIdTokenLoginRequest){
        return ResponseEntity.ok(authService.loginByIdToken(googleIdTokenLoginRequest.getIdToken()));
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