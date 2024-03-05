package com.example.app3.m1.config;

import com.example.app3.m1.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
       // return JacksonUtils.enhancedObjectMapper();
        return JsonUtil.objectMapper();
    }
}
