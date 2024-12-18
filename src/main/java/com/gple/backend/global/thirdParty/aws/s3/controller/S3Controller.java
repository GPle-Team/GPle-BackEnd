package com.gple.backend.global.thirdParty.aws.s3.controller;

import com.gple.backend.global.thirdParty.aws.s3.dto.response.UploadImageResDto;
import com.gple.backend.global.thirdParty.aws.s3.dto.response.UploadImagesResDto;
import com.gple.backend.global.thirdParty.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/image")
    public UploadImageResDto uploadImage(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        String fileUrl = s3Service.uploadImage(multipartFile);
        return new UploadImageResDto(fileUrl);
    }

    @PostMapping("/images")
    public UploadImagesResDto uploadImages(
            @RequestParam(value = "files") List<MultipartFile> multipartFiles
    ) {
        List<String> fileUrls = s3Service.uploadImages(multipartFiles);
        return new UploadImagesResDto(fileUrls);
    }
}
