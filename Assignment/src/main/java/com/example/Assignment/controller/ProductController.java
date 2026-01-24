package com.example.Assignment.controller;

import com.example.Assignment.entity.Category;
import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.CategoryRepository;
import com.example.Assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductRepository pRepo; // DAO truy xuất dữ liệu sản phẩm

    @Autowired
    CategoryRepository cRepo; // DAO truy xuất dữ liệu loại hàng

    /**
     * Tự động cung cấp danh sách loại hàng cho thanh phân loại ở tất cả các trang
     */
    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return cRepo.findAll();
    }

    /**
     * Hiển thị danh sách tất cả sản phẩm tại trang chủ
     */
    @RequestMapping("/home/index")
    public String index(Model model) {
        List<Product> list = pRepo.findAll();
        model.addAttribute("items", list);
        model.addAttribute("view", "home/index"); // Bỏ .html để đồng bộ
        return "layout/index";
    }

    /**
     * XỬ LÝ TÌM KIẾM VÀ LỌC (Hợp nhất để sửa lỗi 400)
     * Phương thức này nhận diện cả từ khóa từ Header và mã loại từ thanh phân loại.
     */
    @RequestMapping("/product/search")
    public String search(
            @RequestParam(value = "keywords", required = false, defaultValue = "") String keywords,
            @RequestParam(value = "category", required = false) String categoryId,
            Model model) {

        // 1. Thực hiện truy vấn kết hợp từ Repository
        List<Product> list = pRepo.searchByNameAndCategory(keywords, categoryId);

        // 2. Truyền dữ liệu ra View
        model.addAttribute("items", list);
        model.addAttribute("keywords", keywords); // Giữ lại từ khóa để hiện lên ô Search

        // 3. Điều hướng về trang danh sách sản phẩm
        model.addAttribute("view", "product/index");
        return "layout/index";
    }

    /**
     * Hiển thị chi tiết sản phẩm và gợi ý các mặt hàng cùng loại
     */
    @RequestMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Optional<Product> product = pRepo.findById(id);
        if (product.isPresent()) {
            Product item = product.get();
            model.addAttribute("item", item);

            // Lấy danh sách sản phẩm cùng loại
            List<Product> relatedItems = pRepo.findByCategoryId(item.getCategory().getId());
            model.addAttribute("relatedItems", relatedItems);
        }

        model.addAttribute("view", "product/detail");
        return "layout/index";
    }
}