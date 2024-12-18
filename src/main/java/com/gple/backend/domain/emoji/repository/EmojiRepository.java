package com.gple.backend.domain.emoji.repository;


import com.gple.backend.domain.emoji.entity.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
    List<Emoji> findEmojiByPostId(Long postId);
}
