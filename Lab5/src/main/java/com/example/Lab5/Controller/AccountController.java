package com.example.Lab5.Controller;

import com.example.Lab5.Service.CookieService;
import com.example.Lab5.Service.ParamService;
import com.example.Lab5.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    CookieService cookieService; // Tiêm CookieService [cite: 159]
    @Autowired
    ParamService paramService;   // Tiêm ParamService [cite: 161]
    @Autowired
    SessionService sessionService; // Tiêm SessionService [cite: 163]

    @GetMapping("/account/login")
    public String login1(Model model) {
        // Dùng CookieService đã viết ở Bài 1 để lấy giá trị
        String user = cookieService.getValue("user");
        model.addAttribute("user", user);
        return "account/login";
    }

    @PostMapping("/account/login")
    public String login2(Model model) {
        // 1. Đọc tham số từ form thông qua ParamService [cite: 164, 166]
        String un = paramService.getString("username", "");
        String pw = paramService.getString("password", "");
        boolean rm = paramService.getBoolean("remember", false);

        // 2. Kiểm tra đăng nhập (un="poly", pw="123") [cite: 169]
        if (un.equals("poly") && pw.equals("123")) {
            // Lưu username vào session [cite: 170, 171]
            sessionService.set("username", un);

            // Xử lý ghi nhớ tài khoản [cite: 172]
            if (rm) {
                // Nếu chọn remember: lưu cookie trong 10 ngày
                cookieService.add("user", un, 10 * 24);
            } else {
                // Ngược lại: xóa cookie cũ [cite: 174]
                cookieService.remove("user");
            }
            model.addAttribute("message", "Login successfully");
        } else {
            model.addAttribute("message", "Invalid username or password");
        }

        return "/account/login";
    }
}