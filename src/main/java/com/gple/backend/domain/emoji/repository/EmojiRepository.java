package com.gple.backend.domain.emoji.repository;


import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;
import com.gple.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {
    Optional<Emoji> findEmojiByUserIdAndEmojiTypeAndPostId(Long userId, EmojiType emojiType, Long postId);

    List<Emoji> findByUser(User user);
}
