package com.example.Assignment.controller;

import com.example.Assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    @Autowired
    CartService cart; // Tiêm Service giỏ hàng đã viết ở bước trước [cite: 76]

    // Hiển thị giao diện giỏ hàng
    @RequestMapping("/cart/view")
    public String view(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("view", "cart/view.html");
        return "layout/index";
    }

    // Thêm sản phẩm vào giỏ [cite: 85]
    @RequestMapping("/cart/add/{id}")
    public String add(@PathVariable("id") Integer id) {
        cart.add(id);
        return "redirect:/cart/view"; // Chuyển hướng về trang xem giỏ hàng
    }

    // Xóa sản phẩm khỏi giỏ [cite: 86]
    @RequestMapping("/cart/remove/{id}")
    public String remove(@PathVariable("id") Integer id) {
        cart.remove(id);
        return "redirect:/cart/view";
    }

    // Cập nhật số lượng sản phẩm [cite: 87]
    @RequestMapping("/cart/update/{id}")
    public String update(@PathVariable("id") Integer id, @RequestParam("qty") Integer qty) {
        cart.update(id, qty);
        return "redirect:/cart/view";
    }

    // Xóa sạch giỏ hàng [cite: 88]
    @RequestMapping("/cart/clear")
    public String clear() {
        cart.clear();
        return "redirect:/cart/view";
    }
}