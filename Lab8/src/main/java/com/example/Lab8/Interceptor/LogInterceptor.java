package com.example.Lab8.Interceptor;

import com.example.Lab8.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Date;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        // 1. Lấy thông tin user từ session
        Account user = (Account) request.getSession().getAttribute("user");

        // 2. Lấy thông tin họ tên (nếu chưa login thì ghi là Guest)
        String fullName = (user != null) ? user.getFullname() : "Guest (Chưa đăng nhập)";

        // 3. Ghi log ra Console
        System.out.println(">>> LOG ACCESS: "
                + "URI: " + request.getRequestURI()
                + " | Time: " + new Date()
                + " | User: " + fullName);

        return true; // Luôn trả về true để yêu cầu tiếp tục được xử lý
    }
}