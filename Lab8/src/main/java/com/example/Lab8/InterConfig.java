package com.example.Lab8;

import com.example.Lab8.Interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Áp dụng bộ lọc cho các đường dẫn nhạy cảm
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**", "/account/**", "/mail/**")
                .excludePathPatterns("/auth/login", "/auth/logoff"); // Không chặn trang login/logout
    }
}