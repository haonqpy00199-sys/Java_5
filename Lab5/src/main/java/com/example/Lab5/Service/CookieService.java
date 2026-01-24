package com.example.Lab5.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CookieService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    // Đọc cookie từ request [cite: 79, 84]
    public Cookie get(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) return cookie;
            }
        }
        return null;
    }

    // Đọc giá trị của cookie [cite: 86, 90]
    public String getValue(String name) {
        Cookie cookie = get(name);
        return cookie != null ? cookie.getValue() : "";
    }

    // Tạo và gửi cookie về client [cite: 92, 98]
    public Cookie add(String name, String value, int hours) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(hours * 3600); // Chuyển từ giờ sang giây
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }

    // Xóa cookie [cite: 100, 103]
    public void remove(String name) {
        add(name, "", 0); // Đặt thời hạn bằng 0 để xóa
    }
}