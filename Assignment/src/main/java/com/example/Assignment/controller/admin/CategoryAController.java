package com.example.Assignment.controller.admin;

import com.example.Assignment.entity.Category;
import com.example.Assignment.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryAController {
    @Autowired
    CategoryRepository cRepo;

    // 1. Liệt kê danh sách loại hàng
    @GetMapping("/index")
    public String index(Model model) {
        Category item = new Category();
        model.addAttribute("item", item);
        List<Category> items = cRepo.findAll();
        model.addAttribute("items", items);
        model.addAttribute("view", "admin/category/index");
        return "layout/index";
    }

    // 2. Chọn một loại hàng để chỉnh sửa (Edit)
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        Category item = cRepo.findById(id).get();
        model.addAttribute("item", item);
        List<Category> items = cRepo.findAll();
        model.addAttribute("items", items);
        model.addAttribute("view", "admin/category/index");
        return "layout/index";
    }

    // 3. Tạo mới hoặc Cập nhật loại hàng (Create/Update)
    @PostMapping("/save")
    public String save(@ModelAttribute("item") Category item) {
        cRepo.save(item);
        return "redirect:/admin/category/index";
    }

    // 4. Xóa loại hàng (Delete)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        try {
            cRepo.deleteById(id);
        } catch (Exception e) {
            // Có thể bị lỗi nếu loại hàng đang chứa sản phẩm
            return "redirect:/admin/category/index?error=Cannot delete category with products!";
        }
        return "redirect:/admin/category/index";
    }
}