package com.gple.backend.global.thirdParty.aws.s3.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.cloud.aws.s3")
public record S3Properties (
        String bucketName
) {
}