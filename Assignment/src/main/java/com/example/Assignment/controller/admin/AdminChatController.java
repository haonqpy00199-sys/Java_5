package com.example.Assignment.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminChatController {

    // Đường dẫn này phải khớp chính xác với link bạn nhấn
    @GetMapping("/admin/chat")
    public String showChatPage(Model model) {
        // Gán view để layout/index.html có thể chèn file admin/chat.html vào
        model.addAttribute("view", "admin/chat");
        return "layout/index";
    }
}