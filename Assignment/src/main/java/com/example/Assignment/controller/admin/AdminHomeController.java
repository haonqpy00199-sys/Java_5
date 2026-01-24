package com.example.Assignment.controller.admin;

import com.example.Assignment.repository.OrderRepository;
import com.example.Assignment.repository.ProductRepository;
import com.example.Assignment.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {
    @Autowired ProductRepository pRepo;
    @Autowired OrderRepository oRepo;
    @Autowired AccountRepository aRepo;

    @GetMapping("/admin/home/index")
    public String index(Model model) {
        // Thống kê sơ bộ các con số
        model.addAttribute("totalProducts", pRepo.count());
        model.addAttribute("totalOrders", oRepo.count());
        model.addAttribute("totalUsers", aRepo.count());

        model.addAttribute("view", "admin/home/index");
        return "layout/index";
    }
}