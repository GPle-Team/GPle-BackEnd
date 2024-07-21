package lib.quick.authservice.domain.auth.controller;

import lib.quick.authservice.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @GetMapping("/login/google")
//    public ResponseEntity<String> getGoogleAuthUrl() {
//        return ResponseEntity.ok(authService.getGoogleLoginUrl());
//    }

//    @GetMapping("/test/google")
//    public ResponseEntity<String> google(@RequestParam String code) {
//        return ResponseEntity.ok(authService.getTestAccess(code));
//    }

    @GetMapping("/callback/google")
    public ResponseEntity<String> callback(@RequestParam String token) throws GeneralSecurityException, IOException {
        return ResponseEntity.ok(authService.getAccessToken(token));
    }
}
