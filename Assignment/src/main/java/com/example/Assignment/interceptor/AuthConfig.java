package com.example.Assignment.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor auth;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auth)
                // Các đường dẫn cần bảo vệ
                .addPathPatterns("/order/**", "/templates/account/**", "/templates/admin/**")
                // QUAN TRỌNG: Loại trừ thư mục hình ảnh và tài nguyên tĩnh
                .excludePathPatterns("/images/**", "/css/**", "/js/**", "/templates/auth/login", "/home/**", "/product/detail/**");
    }
}