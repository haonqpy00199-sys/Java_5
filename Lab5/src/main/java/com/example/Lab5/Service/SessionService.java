package com.example.Lab5.Service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    @Autowired
    HttpSession session;

    // Đọc giá trị attribute [cite: 116, 120]
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) session.getAttribute(name);
    }

    // Ghi giá trị attribute [cite: 122, 126]
    public void set(String name, Object value) {
        session.setAttribute(name, value);
    }

    // Xóa attribute [cite: 128, 130]
    public void remove(String name) {
        session.removeAttribute(name);
    }
}