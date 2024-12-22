package com.gple.backend.domain.emoji.service;

import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
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
                .orElseThrow(() -> new HttpException(ExceptionEnum.NOT_FOUND_EMOJI));

        emojiRepository.delete(emoji);
    }
}
