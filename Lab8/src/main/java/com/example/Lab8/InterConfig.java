package com.example.Lab8;

import com.example.Lab8.Interceptor.AuthInterceptor;
import com.example.Lab8.Interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor; // Bộ lọc bảo mật (Bài 5)

    @Autowired
    LogInterceptor logInterceptor;   // Bộ lọc ghi nhật ký (Bài 6)

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 1. Đăng ký AuthInterceptor: Chặn truy cập trái phép
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**", "/account/**", "/mail/**")
                .excludePathPatterns("/auth/login", "/auth/logoff");

        // 2. Đăng ký LogInterceptor: Ghi lại lịch sử truy cập của user
        // Chúng ta thường ghi log cho cùng các địa chỉ được bảo mật
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/admin/**", "/account/**", "/mail/**");
    }
}