package com.example.Assignment.controller;

import com.example.Assignment.entity.Account;
import com.example.Assignment.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    HttpSession session;

    // --- PHẦN ĐĂNG KÝ (SIGN-UP) ---
    // URL chuẩn: /account/sign-up (Không có chữ templates)
    @GetMapping("/account/sign-up")
    public String signUp(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("view", "account/sign-up");
        return "layout/index";
    }

    @PostMapping("/account/sign-up")
    public String register(@ModelAttribute("account") Account account,
                           @RequestParam("confirm") String confirm,
                           Model model) {
        if (accountRepo.existsById(account.getUsername())) {
            model.addAttribute("message", "Username already exists!");
            model.addAttribute("view", "account/sign-up");
            return "layout/index";
        }
        if (!account.getPassword().equals(confirm)) {
            model.addAttribute("message", "Confirm password does not match!");
            model.addAttribute("view", "account/sign-up");
            return "layout/index";
        }
        account.setAdmin(false);
        account.setActivated(true);
        accountRepo.save(account);
        return "redirect:/auth/login?message=Registration successful! Please login.";
    }

    // --- PHẦN ĐĂNG NHẬP (LOGIN) ---
    // Cả GET và POST phải dùng chung một đường dẫn URL

    @GetMapping("/auth/login")
    public String showLoginForm(Model model, @RequestParam(value = "message", required = false) String message) {
        model.addAttribute("message", message);
        model.addAttribute("view", "auth/login");
        return "layout/index";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        Optional<Account> userOpt = accountRepo.findById(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            Account user = userOpt.get();
            // Lưu thông tin người dùng vào session để SkyShop nhận diện
            session.setAttribute("user", user);

            // Xử lý quay lại trang cũ nếu có
            String secureUri = (String) session.getAttribute("secureUri");
            if (secureUri != null) {
                session.removeAttribute("secureUri");
                return "redirect:" + secureUri;
            }
            return "redirect:/home/index";
        }

        model.addAttribute("message", "Invalid username or password!");
        model.addAttribute("view", "auth/login");
        return "layout/index";
    }

    // --- PHẦN ĐĂNG XUẤT (LOGOUT) ---

    @RequestMapping("/auth/logout")
    public String logout() {
        // Xóa thông tin người dùng khỏi session
        session.removeAttribute("user");
        return "redirect:/home/index";
    }
}