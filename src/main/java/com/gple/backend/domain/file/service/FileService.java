package com.gple.backend.domain.file.service;

import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import com.gple.backend.global.thirdParty.aws.adapter.S3Adapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final S3Adapter s3Adapter;

    public String uploadImage(MultipartFile multipartFile) {
        return s3Adapter.uploadImage(multipartFile);
    }

    public List<String> uploadImages(List<MultipartFile> multipartFiles) {
        if (multipartFiles.isEmpty()) {
            throw new HttpException(ExceptionEnum.NOT_FOUND_FILE);
        } else if (multipartFiles.size() > 3) {
            throw new HttpException(ExceptionEnum.MAX_LENGTH_FILE);
        }

        return multipartFiles.stream().map(this::uploadImage).toList();
    }
}
