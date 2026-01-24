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
    UploadService uploadService; // Tiêm dịch vụ upload để xử lý ảnh

    @Autowired
    HttpSession session;

    // 1. Hiển thị trang hồ sơ cá nhân
    @GetMapping("/profile")
    public String profile(Model model) {
        Account user = (Account) session.getAttribute("user");
        if (user == null) {
            return "redirect:/auth/login";
        }
        // Luôn lấy dữ liệu mới nhất từ DB để hiển thị chính xác ảnh và mật khẩu
        model.addAttribute("account", accountRepo.findById(user.getUsername()).get());
        model.addAttribute("view", "account/profile");
        return "layout/index";
    }

    // 2. Xử lý cập nhật thông tin, mật khẩu và hình ảnh
    @PostMapping("/update")
    public String update(@ModelAttribute("account") Account account,
                         @RequestParam("photoFile") MultipartFile photoFile,
                         Model model) {

        // Bước 1: Xử lý cập nhật ảnh đại diện mới nếu có
        if (!photoFile.isEmpty()) {
            String fileName = uploadService.save(photoFile); // Lưu file vào thư mục static/images
            account.setPhoto(fileName);
        } else {
            // Nếu không chọn ảnh mới, giữ lại tên ảnh cũ từ database
            Account currentAccount = accountRepo.findById(account.getUsername()).orElse(null);
            if (currentAccount != null) {
                account.setPhoto(currentAccount.getPhoto());
            }
        }

        // Bước 2: Lưu các thay đổi (bao gồm password và fullname) vào Database
        accountRepo.save(account);

        // Bước 3: Cập nhật lại User trong Session để các trang khác (như Header) nhận thông tin mới
        session.setAttribute("user", account);

        model.addAttribute("message", "Your profile has been updated successfully!");
        model.addAttribute("view", "account/profile");
        return "layout/index";
    }
}