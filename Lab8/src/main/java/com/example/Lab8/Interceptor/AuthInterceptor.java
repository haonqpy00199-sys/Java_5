package com.example.Lab8.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // Kiểm tra xem trong session đã có user (đăng nhập) chưa
        if (session.getAttribute("user") == null) {
            // Lưu lại địa chỉ mà người dùng đang muốn truy cập
            session.setAttribute("securityUri", request.getRequestURI());

            // Chuyển hướng sang trang đăng nhập
            response.sendRedirect("/auth/login");
            return false; // Chặn không cho vào Controller
        }
        return true; // Cho phép đi tiếp vào Controller
    }
}