package com.gple.backend.domain.auth.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash
public class RefreshToken {
    @Id
    private String id;

    private String token;

    @TimeToLive
    private Long ttl;
}
