package com.gple.backend.domain.file.controller;

import com.gple.backend.domain.file.service.FileService;
import com.gple.backend.domain.file.dto.response.UploadImageResponse;
import com.gple.backend.domain.file.dto.response.UploadImagesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/image")
    public UploadImageResponse uploadImage(
            @RequestParam(value = "file") MultipartFile multipartFile
    ) {
        String fileUrl = fileService.uploadImage(multipartFile);
        return new UploadImageResponse(fileUrl);
    }

    @PostMapping("/images")
    public UploadImagesResponse uploadImages(
            @RequestParam(value = "files") List<MultipartFile> multipartFiles
    ) {
        List<String> fileUrls = fileService.uploadImages(multipartFiles);
        return new UploadImagesResponse(fileUrls);
    }
}
