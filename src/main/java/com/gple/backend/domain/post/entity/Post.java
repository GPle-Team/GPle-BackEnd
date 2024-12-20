package com.gple.backend.domain.post.entity;

import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.tag.entity.Tag;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.util.RoleListConverter;
import com.gple.backend.global.util.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "post")
    private List<Tag> tag;

    @OneToMany(mappedBy = "post")
    private List<Emoji> emoji;

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_url", nullable = false, columnDefinition = "VARCHAR(512)")
    private List<String> imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", nullable = false)
    private Location location;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
