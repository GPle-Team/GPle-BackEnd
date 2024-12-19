package com.gple.backend.domain.emoji.controller;

import com.gple.backend.domain.emoji.controller.dto.request.EmojiRequest;
import com.gple.backend.domain.emoji.service.DeleteEmojiService;
import com.gple.backend.domain.emoji.service.CreateEmojiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emoji")
@RequiredArgsConstructor
public class EmojiController {
    private final CreateEmojiService createEmojiService;
    private final DeleteEmojiService deleteEmojiService;

    @PostMapping
    public ResponseEntity<Void> addEmoji(@RequestBody @Valid EmojiRequest reqDto) {
        createEmojiService.execute(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{emojiId}")
    public ResponseEntity<Void> deleteEmoji(@PathVariable Long emojiId) {
        deleteEmojiService.execute(emojiId);
        return ResponseEntity.noContent().build();
    }
}
