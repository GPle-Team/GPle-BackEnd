package com.gple.backend.domain.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Location {
    GYM("금봉관"),
    PLAYGROUND("운동장"),
    DOMITORY("동행관"),
    HOME("본관"),
    WALKING_TRAIL("산책로");

    private final String name;
}
