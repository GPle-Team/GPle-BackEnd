package com.gple.backend.domain.emoji.service;

import com.gple.backend.domain.emoji.controller.dto.request.EmojiRequest;
import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.repository.EmojiRepository;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEmojiService {
    private final EmojiRepository emojiRepository;
    private final UserUtil userUtil;

    @Transactional
    public void execute(EmojiRequest req) {
        User user = userUtil.getCurrentUser();
        Emoji emoji = emojiRepository.findEmojiByUserAndEmojiTypeAndPostId(user, req.getEmojiType(), req.getPostId())
                .orElseThrow(() -> new HttpException(ExceptionEnum.NOT_FOUND_EMOJI));

        emojiRepository.delete(emoji);
    }
}
