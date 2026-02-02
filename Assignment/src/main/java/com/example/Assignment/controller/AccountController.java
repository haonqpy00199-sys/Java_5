package com.example.Assignment.controller;

import com.example.Assignment.entity.Account;
import com.example.Assignment.repository.AccountRepository;
import com.example.Assignment.service.UploadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UploadService uploadService;

    @Autowired
    HttpSession session;

    // 1. Hiển thị trang hồ sơ cá nhân
    @GetMapping("/profile")
    public String profile(Model model) {
        Account userInSession = (Account) session.getAttribute("user");

        // Kiểm tra Session
        if (userInSession == null) {
            return "redirect:/auth/login";
        }

        // ĐÃ SỬA: Dùng orElse(null) thay vì .get() để tránh lỗi NoSuchElementException
        Account account = accountRepo.findById(userInSession.getUsername()).orElse(null);

        if (account == null) {
            // Nếu Session còn nhưng Database đã bị xóa/thay đổi ID
            session.removeAttribute("user");
            return "redirect:/auth/login?message=Account not found!";
        }

        model.addAttribute("account", account);
        model.addAttribute("view", "account/profile");
        return "layout/index";
    }

    // 2. Xử lý cập nhật thông tin
    @PostMapping("/update")
    public String update(@ModelAttribute("account") Account account,
                         @RequestParam("photoFile") MultipartFile photoFile,
                         Model model) {

        // Tìm tài khoản hiện tại trong DB để lấy lại các thông tin không có trên Form (như Admin, Activated)
        Account currentAccount = accountRepo.findById(account.getUsername()).orElse(null);

        if (currentAccount == null) {
            return "redirect:/auth/login";
        }

        // Bước 1: Xử lý cập nhật ảnh
        if (!photoFile.isEmpty()) {
            String fileName = uploadService.save(photoFile);
            account.setPhoto(fileName);
        } else {
            // Giữ lại ảnh cũ nếu không upload ảnh mới
            account.setPhoto(currentAccount.getPhoto());
        }

        // Bước 2: Bảo toàn các trường quan trọng không có trong Form Profile
        account.setAdmin(currentAccount.getAdmin());
        account.setActivated(currentAccount.getActivated());

        // Bước 3: Lưu vào DB
        accountRepo.save(account);

        // Bước 4: Cập nhật lại Session để Header/UI đồng bộ
        session.setAttribute("user", account);

        model.addAttribute("message", "Your profile has been updated successfully!");
        model.addAttribute("view", "account/profile");
        return "layout/index";
    }
}