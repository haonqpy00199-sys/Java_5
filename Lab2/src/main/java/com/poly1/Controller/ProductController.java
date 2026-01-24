package com.poly1.Controller;

import com.poly1.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    // 👉 BÀI 4 – Hiển thị form + dữ liệu mẫu
    @GetMapping("/form")
    public String form(Model model) {
        // Sản phẩm mẫu (Bài 4)
        model.addAttribute("p1", new Product("iPhone 30", 5000.0));

        // ⭐ DÒNG QUAN TRỌNG – BÀI 3
        model.addAttribute("p2", new Product());

        return "product/form";
    }

    // 👉 BÀI 3 + BÀI 4 – Nhận dữ liệu từ form
    @PostMapping("/save")
    public String save(@ModelAttribute("p2") Product p, Model model) {

        // giữ lại p2 để hiển thị sau khi submit
        model.addAttribute("p2", p);

        // giữ lại p1 để không mất dữ liệu mẫu
        model.addAttribute("p1", new Product("iPhone 30", 5000.0));

        return "product/form";
    }

    // 👉 BÀI 4 – Danh sách sản phẩm
    @ModelAttribute("items")
    public List<Product> getItems() {
        return Arrays.asList(
                new Product("A", 1.0),
                new Product("B", 12.0)
        );
    }
}
