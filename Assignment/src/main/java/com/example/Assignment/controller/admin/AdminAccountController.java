package com.example.Assignment.controller.admin;

import com.example.Assignment.entity.Account;
import com.example.Assignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class AdminAccountController {
    @Autowired
    AccountRepository accountRepo;

    // 1. Hiển thị danh sách tài khoản
    @GetMapping("/index")
    public String index(Model model) {
        List<Account> list = accountRepo.findAll();
        model.addAttribute("accounts", list);
        model.addAttribute("view", "admin/account/index");
        return "layout/index";
    }

    // 2. Chức năng phân quyền nhanh (Toggle Admin)
    @GetMapping("/role/{username}")
    public String toggleRole(@PathVariable("username") String username) {
        accountRepo.findById(username).ifPresent(a -> {
            // SỬA: Chuyển từ isAdmin() sang getAdmin()
            a.setAdmin(!a.getAdmin());
            accountRepo.save(a);
        });
        return "redirect:/admin/account/index";
    }

    // 3. Chức năng kích hoạt/khóa tài khoản nhanh (Toggle Status)
    @GetMapping("/status/{username}")
    public String toggleStatus(@PathVariable("username") String username) {
        accountRepo.findById(username).ifPresent(a -> {
            // SỬA: Chuyển từ isActivated() sang getActivated()
            a.setActivated(!a.getActivated());
            accountRepo.save(a);
        });
        return "redirect:/admin/account/index";
    }
}