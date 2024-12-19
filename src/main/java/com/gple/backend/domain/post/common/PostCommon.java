package com.gple.backend.domain.post.common;

import com.gple.backend.domain.emoji.entity.Emoji;
import com.gple.backend.domain.emoji.entity.EmojiType;

import java.util.List;

public class PostCommon {
    public static Long getEmojiCount(List<Emoji> countEmojis, EmojiType emojiType) {
        return countEmojis.stream().filter(emoji -> emoji.getEmojiType() == emojiType).count();
    }
}
