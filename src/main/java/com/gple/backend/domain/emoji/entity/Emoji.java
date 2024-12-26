package com.gple.backend.domain.emoji.entity;

import com.gple.backend.domain.post.entity.Post;
import com.gple.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = { "user_id", "post_id", "emoji_type" }
    )
)
public class Emoji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "emoji_type")
    private EmojiType emojiType;
}
