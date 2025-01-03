package com.gple.backend.global.security.jwt;

import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import com.gple.backend.domain.auth.controller.dto.common.response.TokenResponse;
import com.gple.backend.domain.auth.controller.dto.common.response.TokenSet;
import com.gple.backend.global.security.dto.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final UserDetailsService userDetailsService;

    @Value("${spring.jwt.access-key}")
    private String accessKey;

    @Value("${spring.jwt.refresh-key}")
    private String refreshKey;

    @Value("${spring.jwt.access-expired}")
    public Long accessExp;

    @Value("${spring.jwt.refresh-expired}")
    public Long refreshExp;

    public TokenSet generateTokenSet(String id){
        return new TokenSet(
            generateToken(id, TokenType.ACCESS_TOKEN),
            generateToken(id, TokenType.REFRESH_TOKEN)
        );
    }

    public TokenResponse generateToken(String id, TokenType tokenType) {
        Long expired = tokenType == TokenType.ACCESS_TOKEN ? accessExp : refreshExp;

        byte[] keyBytes = Base64.getEncoder().encode(tokenType == TokenType.ACCESS_TOKEN
            ? accessKey.getBytes()
            : refreshKey.getBytes()
        );
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        Date expiresDate = new Date(System.currentTimeMillis() + expired);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = dateFormat.format(expiresDate);

        String tokenString = Jwts.builder()
            .signWith(signingKey)
            .subject(String.valueOf(id))
            .issuedAt(new Date())
            .expiration(expiresDate)
            .compact();

        return TokenResponse.builder()
            .token(tokenString)
            .tokenType(tokenType)
            .expires(formattedDate)
            .build();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(
            getTokenSubject(token, TokenType.ACCESS_TOKEN)
        );
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getTokenSubject(String subject, TokenType tokenType) {
        return getClaims(subject, tokenType).getSubject();
    }

    public Claims getClaims(String token, TokenType tokenType) {
        byte[] keyBytes = Base64.getEncoder().encode((tokenType == TokenType.ACCESS_TOKEN
            ? accessKey
            : refreshKey
        ).getBytes());
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        try {
            return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            throw new HttpException(ExceptionEnum.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            throw new HttpException(ExceptionEnum.UNSUPPORTED_JWT);
        } catch (MalformedJwtException e) {
            throw new HttpException(ExceptionEnum.MALFORMED_JWT);
        } catch (SignatureException e) {
            throw new HttpException(ExceptionEnum.INVALID_SIGNATURE);
        } catch (RuntimeException e) {
            throw new HttpException(ExceptionEnum.UNEXPECTED_EXCEPTION);
        }
    }

    public Boolean validateToken(String token){
        byte[] keyBytes = Base64.getEncoder().encode(accessKey.getBytes());
        SecretKey signingKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration()
            .before(new Date());
    }
}