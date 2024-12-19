package com.gple.backend.domain.file.dto.response;

import java.util.List;

public record UploadImagesResponse(
        List<String> urls
) {
}
