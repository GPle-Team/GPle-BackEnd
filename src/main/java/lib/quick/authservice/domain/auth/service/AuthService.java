package lib.quick.authservice.domain.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import lib.quick.authservice.domain.member.entity.Member;
import lib.quick.authservice.domain.member.entity.Role;
import lib.quick.authservice.domain.member.repository.MemberRepository;
import lib.quick.authservice.global.exception.HttpException;
import lib.quick.authservice.global.security.dto.TokenType;
import lib.quick.authservice.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Value("${spring.google.client-id}")
    private String clientId;

    @Value("${spring.google.client-secret}")
    private String clientSecret;

    @Value("${spring.google.redirect-uri}")
    private String redirectUri;

    public String getGoogleLoginUrl(){
        String scope = "email profile";

        String url = UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", redirectUri)
            .queryParam("response_type", "code")
            .queryParam("scope", scope)
            .toUriString();

        return url;
    }

    public String getTestAccess(String code){
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
        return response.toString();
    }

    public String getAccessToken(String idToken) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory factory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier(transport, factory);

        GoogleIdToken googleIdToken = GoogleIdToken.parse(factory, idToken);
        Payload payload = googleIdToken.getPayload();

        if(!verifier.verify(googleIdToken)){
            throw new HttpException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }

        String username = payload.getSubject();
        String email = payload.getEmail();
        String name = googleIdToken.getPayload().get("name").toString();

        if(!email.endsWith("gsm.hs.kr")){
            throw new HttpException(HttpStatus.BAD_REQUEST, "gsm.hs.kr 도메인만 등록할 수 있습니다.");
        }

        Member member;
        if(memberRepository.existsByEmail(email)){
            member = memberRepository.findByEmail(email).get();
            member.setName(name);
        } else {
            member = Member.builder()
                .id(UUID.randomUUID())
                .role(Role.ROLE_USER)
                .username(username)
                .email(email)
                .name(name)
                .build();
        }

        memberRepository.save(member);

        return jwtProvider.generateToken(username, TokenType.ACCESS_TOKEN);
    }
}
