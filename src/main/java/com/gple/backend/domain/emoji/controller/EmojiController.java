package com.gple.backend.domain.emoji.controller;

import com.gple.backend.domain.emoji.controller.dto.EmojiReqDto;
import com.gple.backend.domain.emoji.service.EmojiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emoji")
@RequiredArgsConstructor
public class EmojiController {
    private final EmojiService emojiService;

    @PostMapping("/add")
    public ResponseEntity<Void> addEmoji(@RequestBody @Valid EmojiReqDto reqDto) {
        emojiService.execute(reqDto);
        return ResponseEntity.ok().build();
    }
}
