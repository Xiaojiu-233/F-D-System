package com.sxu.fdsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径
                .allowedOriginPatterns("*") // 允许跨域访问的源
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS") // 允许请求方法
                .maxAge(168000) // 预检时间间隔
                .allowedHeaders("*") // 允许头部设置
                .allowCredentials(true); // 是否发送cookie
    }
}
