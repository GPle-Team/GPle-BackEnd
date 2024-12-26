package com.gple.backend.domain.emoji.repository;


import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
    Emoji findEmojiByUserIdAndEmojiTypeAndPostId(Long userId, EmojiType emojiType, Long postId);

    List<Emoji> findByUserId(Long userId);
}
