package com.example.Assignment.controller;

import com.example.Assignment.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    CategoryRepository cRepo;

    @ModelAttribute
    public void addAttributes(Model model) {
        // Tự động thêm danh sách categories vào Model của TẤT CẢ các trang
        model.addAttribute("categories", cRepo.findAll());
    }
}