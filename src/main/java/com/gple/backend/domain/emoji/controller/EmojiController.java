package com.gple.backend.domain.emoji.controller;

import com.gple.backend.domain.emoji.controller.dto.request.EmojiRequest;
import com.gple.backend.domain.emoji.service.ToggleEmojiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emoji")
@RequiredArgsConstructor
public class EmojiController {
    private final ToggleEmojiService toggleEmojiService;

    @PostMapping
    public ResponseEntity<Void> toggleEmoji(@RequestBody @Valid EmojiRequest reqDto) {
        toggleEmojiService.execute(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
