package com.gple.backend.domain.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.gple.backend.domain.auth.controller.dto.common.response.TokenSet;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.gple.backend.domain.auth.controller.dto.common.response.GoogleOAuthToken;
import com.gple.backend.domain.auth.controller.dto.common.response.GoogleUserInfo;
import com.gple.backend.domain.auth.controller.dto.common.response.TokenResponse;
import com.gple.backend.domain.auth.controller.dto.web.response.RefreshTokenResponse;
import com.gple.backend.domain.auth.controller.dto.web.response.WebTokenResponse;
import com.gple.backend.domain.auth.entity.RefreshToken;
import com.gple.backend.domain.auth.repository.RefreshTokenRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.entity.Role;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.exception.HttpException;
import com.gple.backend.global.security.dto.TokenType;
import com.gple.backend.global.security.jwt.JwtProvider;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JsonFactory jsonFactory;
    private final HttpTransport httpTransport;
    private final UserUtil userUtil;
    private final JwtProvider jwtProvider;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Transactional
    public RefreshTokenResponse refreshToken(String refreshToken){
        String slicedToken = refreshToken.substring(7);
        String id = jwtProvider.getClaims(slicedToken, TokenType.REFRESH_TOKEN).getSubject();

        if(!refreshTokenRepository.existsById(id)){
            throw new HttpException(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다.");
        }

        TokenResponse generatedToken = jwtProvider.generateToken(id, TokenType.ACCESS_TOKEN);
        return new RefreshTokenResponse(generatedToken.getToken(), generatedToken.getExpires());
    }

    @Transactional
    public WebTokenResponse googleLogin(String code){
        GoogleOAuthToken googleOAuthToken = getGoogleTokens(code);
        GoogleUserInfo userInfo = getGoogleUserInfo(googleOAuthToken.getAccess_token());
        String email = userInfo.getEmail();

        validEmail(email);
        existOrSaveUser(email);

        User user = userRepository.findByEmail(email).orElseThrow(() ->
            new HttpException(HttpStatus.BAD_REQUEST, "예기치 않은 오류로 유저를 찾을 수 없습니다.")
        );

        TokenSet tokenSet = jwtProvider.generateTokenSet(user.getId().toString());
        TokenResponse refreshToken = tokenSet.getRefreshToken();

        saveRefreshToken(user.getId(), refreshToken.getToken());

        return tokenSet.toWebTokenResponse();
    }

    @Transactional
    public void logout(String refreshToken){
        if(refreshToken != null){
            String slicedToken = refreshToken.substring(7);

            String tokenId = jwtProvider.getClaims(slicedToken, TokenType.REFRESH_TOKEN).getSubject();
            User user = userUtil.getCurrentUser();
            String id = user.getId().toString();

            if(tokenId.equals(id)) refreshTokenRepository.deleteById(id);
            else throw new HttpException(HttpStatus.NOT_FOUND, "토큰과 일치하는 유저를 찾을 수 없습니다.");
        }
    }

    private void validEmail(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@gsm\\.hs\\.kr$");
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new HttpException(HttpStatus.BAD_REQUEST, "gsm.hs.kr 도메인만 사용할 수 있습니다.");
        }
    }

    // 해당 이메일의 유저가 존재하지 않으면 유저 생성
    private void existOrSaveUser(String email){
        if(!userRepository.existsByEmail(email)){
            User joinUser = User.builder()
                .email(email)
                .roles(List.of(Role.ROLE_USER))
                .build();
            userRepository.save(joinUser);
        }
    }

    private GoogleOAuthToken getGoogleTokens(String code) {
        GoogleTokenResponse googleTokenResponse = getGoogleTokenResponse(code);

        return GoogleOAuthToken.builder()
            .access_token(googleTokenResponse.getAccessToken())
            .scope(googleTokenResponse.getScope())
            .token_type(googleTokenResponse.getTokenType())
            .expires_in(googleTokenResponse.getExpiresInSeconds())
            .build();
    }

    private GoogleUserInfo getGoogleUserInfo(String accessToken){
        WebClient client = WebClient.create("https://www.googleapis.com");
        ResponseEntity<GoogleUserInfo> response = client.get()
            .uri("/oauth2/v2/userinfo")
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .toEntity(GoogleUserInfo.class)
            .block();

        if(response == null){
            throw new HttpException(HttpStatus.UNAUTHORIZED, "구글 엑세스 토큰이 유효하지 않습니다.");
        }

        return response.getBody();
    }

    private void saveRefreshToken(Long id, String token){
        RefreshToken refreshToken = RefreshToken.builder()
            .id(id.toString())
            .token(token)
            .ttl(172800000L)
            .build();

        refreshTokenRepository.save(refreshToken);
    }

    private GoogleTokenResponse getGoogleTokenResponse(String code){
        try {
            return new GoogleAuthorizationCodeTokenRequest(
                httpTransport, jsonFactory,
                "https://oauth2.googleapis.com/token",
                clientId,
                clientSecret,
                code,
                redirectUri
            ).execute();
        } catch (IOException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "구글 엑세스 토큰을 받아오던 중 오류가 발생하였습니다.");
        }
    }
}
