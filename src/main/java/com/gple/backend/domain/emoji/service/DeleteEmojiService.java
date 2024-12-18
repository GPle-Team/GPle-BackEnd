package com.gple.backend.domain.emoji.service;

import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.global.exception.ExpectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEmojiService {
    private final EmojiRepository emojiRepository;

    @Transactional
    public void execute(Long emojiId) {
        Emoji emoji = emojiRepository.findById(emojiId)
                .orElseThrow(() -> new ExpectedException("이모지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        emojiRepository.delete(emoji);
    }
}
