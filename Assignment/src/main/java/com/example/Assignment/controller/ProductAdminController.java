package com.example.Assignment.controller;

import com.example.Assignment.entity.Category;
import com.example.Assignment.entity.Product;
import com.example.Assignment.repository.CategoryRepository;
import com.example.Assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/templates/admin/product")
public class ProductAdminController {
    @Autowired
    ProductRepository pRepo;
    @Autowired
    CategoryRepository cRepo;

    // Hiển thị danh sách và Form quản lý [cite: 121]
    @RequestMapping("/index")
    public String index(Model model) {
        Product item = new Product();
        model.addAttribute("item", item);
        model.addAttribute("items", pRepo.findAll());
        model.addAttribute("view", "templates/admin/product.html");
        return "layout/index";
    }

    // Đổ dữ liệu từ bảng lên Form để sửa [cite: 122]
    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("item", pRepo.findById(id).get());
        model.addAttribute("items", pRepo.findAll());
        model.addAttribute("view", "templates/admin/product.html");
        return "layout/index";
    }

    // Xử lý thêm mới hoặc cập nhật [cite: 124, 125]
    @RequestMapping("/save")
    public String save(Product item, @RequestParam("attach") MultipartFile file) {
        if(!file.isEmpty()) {
            // Xử lý lưu file ảnh vào thư mục static/images [cite: 74]
            String filename = file.getOriginalFilename();
            item.setImage(filename);
        }
        pRepo.save(item);
        return "redirect:/admin/product/index";
    }

    // Xử lý xóa sản phẩm [cite: 123]
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        pRepo.deleteById(id);
        return "redirect:/admin/product/index";
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return cRepo.findAll();
    }
}