package com.gple.backend.global.util;

import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {
    public String generateFileName(String extension) {
        return UUID.randomUUID() + "." + extension;
    }

    public String getFileExtension(String filename) {
        String extension = Arrays.stream(filename.split("\\.")).toList().getLast();
        return extension;
    }

    public void validateImageExtension(String fileExtension) {
        List<String> allowExtensions = List.of("jpg", "jpeg", "png");
        if (!allowExtensions.contains(fileExtension.toLowerCase())) {
            throw new HttpException(ExceptionEnum.INVALID_FILE_FORMAT);
        }
    }
}
