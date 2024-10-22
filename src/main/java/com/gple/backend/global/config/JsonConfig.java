package com.gple.backend.global.config;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
    @Bean
    public JsonFactory jsonFactory(){
        return GsonFactory.getDefaultInstance();
    }
}
