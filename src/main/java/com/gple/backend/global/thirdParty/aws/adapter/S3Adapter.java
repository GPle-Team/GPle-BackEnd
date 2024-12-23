package com.gple.backend.global.thirdParty.aws.adapter;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import com.gple.backend.global.thirdParty.aws.properties.S3Properties;
import com.gple.backend.global.util.FileUtil;
import com.gple.backend.global.util.ImageUtil;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Adapter {
    private final S3Template s3Template;
    private final S3Properties properties;
    private final FileUtil fileUtil;
    private final ImageUtil imageUtil;

    public String uploadImage(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;

        String extension = fileUtil.getFileExtension(originalFilename);
        fileUtil.validateImageExtension(extension);

        String filename = fileUtil.generateFileName(extension);

        try {
            multipartFile = imageUtil.resizeMultipartFile(multipartFile, filename);
        } catch (IOException e) {
            throw new HttpException(ExceptionEnum.UNEXPECTED_IMAGE_CONVERT);
        } catch (ImageProcessingException | MetadataException e) {
            log.info(e.getLocalizedMessage());
        }

        return this.uploadFile(multipartFile);
    }

    public String uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new HttpException(ExceptionEnum.NOT_FOUND_FILE);
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        String fileName = fileUtil.generateFileName(fileExtension);

        try {
            S3Resource s3Resource = s3Template.upload(
                properties.bucketName(),
                fileName,
                multipartFile.getInputStream(),
                ObjectMetadata.builder().contentType(fileExtension).build()
            );

            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new HttpException(ExceptionEnum.INVALID_INPUT_OUTPUT);
        } catch (S3Exception e) {
            throw new HttpException(ExceptionEnum.INVALID_AWS_S3);
        } catch (AwsServiceException e) {
            throw new HttpException(ExceptionEnum.INVALID_AWS);
        } catch (SdkClientException e) {
            throw new HttpException(ExceptionEnum.INVALID_AWS_SDK_CLIENT);
        }
    }
}
