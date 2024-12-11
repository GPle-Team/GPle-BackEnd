package com.gple.backend.global.thirdParty.aws.s3.dto.response;

import java.util.List;

public record UploadImagesResDto(
        List<String> urls
) {
}
