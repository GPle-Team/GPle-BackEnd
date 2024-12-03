package com.gple.backend.domain.post.entity;

import com.gple.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;
}
