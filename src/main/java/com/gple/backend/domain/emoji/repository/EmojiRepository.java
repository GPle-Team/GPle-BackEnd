package com.gple.backend.domain.emoji.repository;


import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
    Optional<Emoji> findByPostAndEmojiType(Post post, EmojiType emojiType);
}
