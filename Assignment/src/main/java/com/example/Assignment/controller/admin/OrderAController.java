package com.example.Assignment.controller.admin;

import com.example.Assignment.entity.Order;
import com.example.Assignment.repository.OrderRepository;
import com.example.Assignment.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderAController {
    @Autowired OrderRepository oRepo;
    @Autowired OrderDetailRepository dRepo;

    // 1. Hiển thị danh sách đơn hàng
    @GetMapping("/index")
    public String index(Model model) {
        // Lấy tất cả đơn hàng, bạn có thể sắp xếp theo ngày mới nhất
        List<Order> items = oRepo.findAll();
        model.addAttribute("orders", items);
        model.addAttribute("view", "admin/order/index");
        return "layout/index";
    }

    // 2. Xem chi tiết một đơn hàng
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Order order = oRepo.findById(id).get();
        model.addAttribute("order", order);
        // Lấy các mặt hàng nằm trong đơn hàng này
        model.addAttribute("details", dRepo.findByOrderId(id));
        model.addAttribute("view", "admin/order/detail");
        return "layout/index";
    }

    // 3. Xóa đơn hàng (Nếu cần)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        oRepo.deleteById(id);
        return "redirect:/admin/order/index";
    }
}