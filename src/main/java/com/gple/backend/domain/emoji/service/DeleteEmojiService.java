package com.gple.backend.domain.emoji.service;

import com.gple.backend.domain.emoji.controller.dto.request.EmojiRequest;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEmojiService {
    private final EmojiRepository emojiRepository;

    @Transactional
    public void execute(EmojiRequest req) {
        Emoji emoji = emojiRepository.findEmojiByEmojiTypeAndPostId(req.getEmojiType(), req.getPostId());
        if (emoji != null)
            emojiRepository.delete(emoji);
    }
}
