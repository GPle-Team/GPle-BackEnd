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

    @DeleteMapping
    public ResponseEntity<Void> deleteEmoji(@RequestBody @Valid EmojiRequest reqDto) {
        deleteEmojiService.execute(reqDto);
        return ResponseEntity.noContent().build();
    }
}
