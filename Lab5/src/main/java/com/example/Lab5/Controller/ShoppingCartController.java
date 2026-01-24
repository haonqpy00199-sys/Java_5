package com.example.Lab5.Controller;

import com.example.Lab5.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShoppingCartController {
    @Autowired
    ShoppingCartService cart; // Tiêm Spring Bean đã viết [cite: 348]

    @RequestMapping("/cart/add/{id}")
    public String add(@PathVariable("id") Integer id) {
        cart.add(id); // Thêm mặt hàng vào giỏ [cite: 190]
        return "redirect:/cart/view"; // Hiển thị giỏ hàng [cite: 356]
    }

    @RequestMapping("/cart/view")
    public String view(Model model) {
        model.addAttribute("cart", cart); // Chuyển dữ liệu giỏ hàng sang giao diện [cite: 344, 352]
        return "cart/index";
    }

    @RequestMapping("/cart/remove/{id}")
    public String remove(@PathVariable("id") Integer id) {
        cart.remove(id); // Xóa mặt hàng khỏi giỏ [cite: 195, 356]
        return "redirect:/cart/view";
    }

    @PostMapping("/cart/update/{id}")
    public String update(@PathVariable("id") Integer id, @RequestParam("qty") Integer qty) {
        cart.update(id, qty); // Thay đổi số lượng mặt hàng [cite: 202, 357]
        return "redirect:/cart/view";
    }

    @RequestMapping("/cart/clear")
    public String clear() {
        cart.clear(); // Xóa sạch các mặt hàng trong giỏ [cite: 206, 358]
        return "redirect:/cart/view";
    }
}