package com.example.Lab8.Controller;

import com.example.Lab8.Service.AccountService;
import com.example.Lab8.entity.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @GetMapping("/auth/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/auth/login")
    public String loginProcess(Model model,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password) {

        // 1. Tìm kiếm tài khoản
        Account user = accountService.findById(username);

        if (user == null) {
            model.addAttribute("message", "Sai tên đăng nhập!");
        } else if (!user.getPassword().equals(password)) {
            model.addAttribute("message", "Sai mật khẩu!");
        } else {
            // 2. Đăng nhập thành công: Lưu user vào session
            session.setAttribute("user", user);

            // 3. Xử lý quay lại trang cũ (Bài 5)
            String securityUri = (String) session.getAttribute("securityUri");
            if (securityUri != null) {
                session.removeAttribute("securityUri"); // Xóa ngay sau khi lấy ra
                return "redirect:" + securityUri;
            }

            // Nếu không có trang chờ thì mặc định về trang chủ hoặc trang mail
            return "redirect:/mail/index";
        }
        return "auth/login";
    }

    @RequestMapping("/auth/logoff")
    public String logoff() {
        session.removeAttribute("user");
        session.removeAttribute("securityUri");
        return "redirect:/auth/login";
    }
}