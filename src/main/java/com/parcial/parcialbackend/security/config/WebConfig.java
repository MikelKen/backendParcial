package com.parcial.parcialbackend.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
    
    @SuppressWarnings("null")
    @Override
    public void addCorsMappings( CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("POST","GET","PUT","DELETE")
            .allowedHeaders("*")
            .exposedHeaders("Authorization")
            .maxAge(3600);
    }
}
