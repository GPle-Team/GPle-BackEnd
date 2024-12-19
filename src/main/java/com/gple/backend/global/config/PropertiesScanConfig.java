package com.gple.backend.global.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = {
        "com.gple.backend.global.thirdParty.aws.properties"})
public class PropertiesScanConfig {
}
