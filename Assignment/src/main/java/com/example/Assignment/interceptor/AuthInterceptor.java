package com.example.Assignment.interceptor;

import com.example.Assignment.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Account user = (Account) request.getSession().getAttribute("user");

        String message = "";

        // 1. Kiểm tra đăng nhập cho các vùng nhạy cảm
        // Chặn các đường dẫn bắt đầu bằng /admin/, /account/, /order/, /cart/checkout
        if (user == null) {
            if (uri.startsWith("/admin/") || uri.startsWith("/account/") || uri.startsWith("/order/")) {
                message = "Please login to access this page!";
            }
        }
        // 2. Đã đăng nhập nhưng truy cập vào vùng Admin mà không có quyền
        else if (uri.startsWith("/admin/") && !user.getAdmin()) {
            message = "Access denied! Admin role is required.";
        }
        // 3. Kiểm tra tài khoản có bị khóa hay không
        else if (!user.getActivated()) {
            request.getSession().removeAttribute("user"); // Đăng xuất nếu bị khóa
            message = "Your account has been deactivated. Please contact support.";
        }

        if (!message.isEmpty()) {
            // Lưu lại URL người dùng định vào để quay lại sau khi login
            request.getSession().setAttribute("secureUri", uri);

            // CHÚ Ý: Chuyển hướng tới endpoint của Controller (/auth/login) chứ không phải file /templates/
            response.sendRedirect("/auth/login?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8));
            return false;
        }

        return true;
    }
}