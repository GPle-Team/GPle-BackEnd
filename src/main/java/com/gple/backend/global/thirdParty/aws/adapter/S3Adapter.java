package com.gple.backend.global.thirdParty.aws.adapter;

import com.gple.backend.global.exception.ExpectedException;
import com.gple.backend.global.thirdParty.aws.properties.S3Properties;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Adapter {
    private final S3Template s3Template;
    private final S3Properties properties;

    public String uploadImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new ExpectedException("파일이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        validateFileExtensionType(Objects.requireNonNull(fileExtension));
        String fileName = generateFileName(fileExtension);

        try {
            S3Resource s3Resource = s3Template.upload(
                properties.bucketName(),
                fileName,
                multipartFile.getInputStream(),
                ObjectMetadata.builder().contentType(fileExtension).build()
            );

            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new RuntimeException("입출력 작업중에 예외 발생", e);
        } catch (S3Exception e) {
            throw new RuntimeException("S3에서 예외 발생", e);
        } catch (AwsServiceException e) {
            throw new RuntimeException("AWS에서 예외 발생", e);
        } catch (SdkClientException e) {
            throw new RuntimeException("클라이언측 문제로 인한 예외 발생", e);
        }
    }

    private String generateFileName(String fileExtension) {
        return UUID.randomUUID() + "." + fileExtension;
    }

    private void validateFileExtensionType(String fileExtention) {
        List<String> allowExtensions = List.of("jpg", "jpeg", "png");
        if (!allowExtensions.contains(fileExtention.toLowerCase())) {
            throw new ExpectedException("지원하지 않는 파일 확장자입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
