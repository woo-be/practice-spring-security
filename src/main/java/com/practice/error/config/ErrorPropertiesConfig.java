package com.practice.error.config;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorPropertiesConfig {

    @Bean
    public ErrorProperties errorProperties() {
        return new ErrorProperties();
    }
}