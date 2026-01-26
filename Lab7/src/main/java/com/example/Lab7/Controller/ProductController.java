package com.example.Lab7.Controller;

import com.example.Lab7.Controller.Service.SessionService;
import com.example.Lab7.DAO.ProductDAO;
import com.example.Lab7.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductDAO dao;

    @Autowired
    SessionService session; // Sử dụng để lưu trữ từ khóa tìm kiếm

    // --- Bài 1: Tìm kiếm theo khoảng giá ---
    @RequestMapping("/product/search")
    public String search(Model model,
                         @RequestParam("min") Optional<Double> min,
                         @RequestParam("max") Optional<Double> max) {

        double minPrice = min.orElse(Double.MIN_VALUE);
        double maxPrice = max.orElse(Double.MAX_VALUE);

        // Bạn có thể chọn 1 trong 2 dòng dưới đây để chạy:
        // List<Product> items = dao.findByPrice(minPrice, maxPrice); // Theo Bài 1
        List<Product> items = dao.findByPriceBetween(minPrice, maxPrice); // Theo Bài 4

        model.addAttribute("items", items);
        return "product/search";
    }

    // --- Bài 2: Tìm kiếm theo tên & Phân trang ---
    @RequestMapping("/product/search-and-page")
    public String searchAndPage(Model model,
                                @RequestParam("keywords") Optional<String> kw,
                                @RequestParam("p") Optional<Integer> p) {

        String kwords = kw.orElse(session.get("keywords", ""));
        session.set("keywords", kwords);

        Pageable pageable = PageRequest.of(p.orElse(0), 5);

        // Cập nhật: Thay findByKeywords bằng phương thức DSL mới
        Page<Product> page = dao.findAllByNameLike("%" + kwords + "%", pageable);

        model.addAttribute("page", page);
        return "product/search-and-page";
    }

}