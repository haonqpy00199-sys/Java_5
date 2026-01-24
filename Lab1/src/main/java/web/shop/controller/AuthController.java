package web.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthController {

    @Autowired
    HttpServletRequest request;

    // 👉 /login/form
    @GetMapping("/form")
    public String form() {
        return "demo/login";
    }

    // 👉 /login/check
    @PostMapping("/check")
    public String login(Model model) {
        // Đọc dữ liệu từ request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Kiểm tra đăng nhập
        if ("poly".equals(username) && "123".equals(password)) {
            model.addAttribute("message", "Đăng nhập thành công");
        } else {
            model.addAttribute("message", "Đăng nhập thất bại");
        }

        return "demo/login";
    }
}
