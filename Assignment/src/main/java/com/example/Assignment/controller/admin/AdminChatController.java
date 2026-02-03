package com.example.Assignment.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminChatController {
    // Đổi từ /admin/chat thành /support-center để khách hàng không bị chặn
    @GetMapping("/support-center")
    public String index(Model model) {
        model.addAttribute("view", "admin/chat");
        return "layout/index";
    }
}