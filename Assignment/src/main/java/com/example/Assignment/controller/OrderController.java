package com.example.Assignment.controller;

import com.example.Assignment.entity.Account;
import com.example.Assignment.entity.Order;
import com.example.Assignment.entity.OrderDetail;
import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.OrderDetailRepository;
import com.example.Assignment.repository.OrderRepository;
import com.example.Assignment.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    CartService cart;
    @Autowired
    OrderRepository oRepo;
    @Autowired
    OrderDetailRepository dRepo;
    @Autowired
    HttpSession session;

    /**
     * Hiển thị trang xác nhận thanh toán
     */
    @RequestMapping("/order/checkout")
    public String checkout(Model model) {
        model.addAttribute("cart", cart);
        // ĐÃ SỬA: Bỏ .html để tránh lỗi TemplateInputException
        model.addAttribute("view", "order/checkout");
        return "layout/index";
    }

    /**
     * Xử lý lưu đơn hàng (Purchase)
     */
    @PostMapping("/order/purchase") // Dùng PostMapping cho bảo mật dữ liệu
    public String purchase(@RequestParam("address") String address) {
        Account user = (Account) session.getAttribute("user");

        // Kiểm tra đăng nhập để tránh lỗi NullPointerException
        if (user == null) {
            return "redirect:/auth/login?message=Please login to purchase!";
        }

        // 1. Tạo đơn hàng mới
        Order order = new Order();
        order.setAccount(user);
        order.setAddress(address);
        order.setCreateDate(new Date());
        oRepo.save(order);

        // 2. Lưu chi tiết đơn hàng
        for (Product p : cart.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(p);
            detail.setPrice(p.getPrice());
            detail.setQuantity(p.getQty());
            dRepo.save(detail);
        }

        cart.clear(); // Xóa giỏ hàng sau khi mua
        return "redirect:/order/detail/" + order.getId(); // Chuyển thẳng tới trang chi tiết đơn vừa mua
    }

    /**
     * Hiển thị danh sách lịch sử đơn hàng
     */
    @RequestMapping("/order/list")
    public String list(Model model) {
        Account user = (Account) session.getAttribute("user");
        if (user == null) return "redirect:/auth/login";

        List<Order> orders = oRepo.findByAccountOrderByCreateDateDesc(user);
        model.addAttribute("orders", orders);
        // ĐÃ SỬA: Bỏ .html
        model.addAttribute("view", "order/list");
        return "layout/index";
    }

    /**
     * Xem chi tiết hóa đơn
     */
    @RequestMapping("/order/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        // Tìm đơn hàng, nếu không thấy sẽ ném lỗi hoặc xử lý tùy ý
        Order order = oRepo.findById(id).orElse(null);

        if (order == null) {
            return "redirect:/order/list";
        }

        model.addAttribute("order", order);
        // ĐÃ SỬA: Bỏ .html để khớp với file templates/order/detail.html bạn đã tạo
        model.addAttribute("view", "order/detail");
        return "layout/index";
    }
}