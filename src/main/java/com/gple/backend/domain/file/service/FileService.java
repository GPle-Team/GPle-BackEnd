package com.gple.backend.domain.file.service;

import com.gple.backend.global.exception.ExpectedException;
import com.gple.backend.global.file.ImageMultipartFile;
import com.gple.backend.global.thirdParty.aws.properties.S3Properties;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
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
            multipartFile = resizeImage(multipartFile);
        } catch (IOException e){
            throw new ExpectedException("파일 사이즈를 변환하는 과정에서 문제가 생겼습니다.", HttpStatus.BAD_REQUEST);
        }

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

    public List<String> uploadImages(List<MultipartFile> multipartFiles) {
        if (multipartFiles.isEmpty()) {
            throw new ExpectedException("파일이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        } else if (multipartFiles.size() > 3) {
            throw new ExpectedException("파일은 최대 3개까지 업로드 가능합니다.", HttpStatus.BAD_REQUEST);
        }
        List<String> fileList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

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

                fileList.add(s3Resource.getURL().toString());
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

        return fileList;
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

    private MultipartFile resizeImage(MultipartFile imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile.getInputStream());

        int originWidth = image.getWidth();
        int originHeight = image.getHeight();

        int changedWidth, changedHeight;
        if(originWidth > originHeight){
            changedWidth = 390;
            changedHeight = (originHeight * 390) / originWidth;
        } else {
            changedHeight = 390;
            changedWidth = (originWidth * 390) / originHeight;
        }

        Image newImage = image.getScaledInstance(changedWidth, changedHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(changedWidth, changedHeight, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = resizedImage.getGraphics();
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);
        byte[] imageByte = outputStream.toByteArray();

        return new ImageMultipartFile(imageByte, generateFileName("jpg"), "image/jpeg");
    }
}
