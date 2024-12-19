package com.gple.backend.global.config;

import com.gple.backend.global.thirdParty.aws.properties.S3Properties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
public class PropertiesScanConfig {
}
